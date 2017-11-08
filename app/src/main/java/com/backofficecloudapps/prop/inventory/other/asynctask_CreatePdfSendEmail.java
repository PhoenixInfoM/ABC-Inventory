package com.backofficecloudapps.prop.inventory.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.backofficecloudapps.prop.inventory.Model.Adresses;
import com.backofficecloudapps.prop.inventory.database.TableInventories;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.database.db_MainStatic;
import com.backofficecloudapps.prop.inventory.database.db_PdfView;
import com.backofficecloudapps.prop.inventory.utils.Utility;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.backofficecloudapps.prop.inventory.database.db_PdfView.MULTIID;
import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_AFTER_PURCHASE_PDF_COUNT;

public class asynctask_CreatePdfSendEmail extends AsyncTask<String, String, String> {

    private List<String> zeroLevelElements;
    private Font bold;
    private Font normal;
    private Font small;
    private Font title;
    @NonNull
    private String titles[] = {"No", "Item", "Description", "Comment", "Picture"};
    private String inventoryDate;
    private String propertyID;
    private String inventoryName="";
    @NonNull
    private String PostName="";
    private ProgressDialog progressDialog;
    private Activity context;

    private String fileName;
    private Adresses item;
    PdfWriter writer;

    db_DesireOutput dbClient;//= new db_DesireOutput(asynctask_CreatePdfSendEmail.this);
    db_MainStatic dbMain;// = new db_MainStatic(activity_ViewSaveData.this);
    db_PdfView dbPdfView;

    private List<String> zeroLevelElementCode;
    private List<TableInventories> dbHolder;
    // use for check created pdf counter
    SharedPreferences sharedPreference;
    @NonNull
    public static String PreferName = "CountPdf";
    @NonNull
    public static String PreferKey  = "PDF";

    String FURNISHED="";
    @NonNull
    String TITLE_FURNISHED="";

    int shared_data;//=  sharedPreference.getInt(PreferKey,0);

    public asynctask_CreatePdfSendEmail(@NonNull Activity context, String propertyID, String inventoryName, String inventoryDate, Adresses item) {
        this.context = context;
        this.propertyID = propertyID;
        this.inventoryName = inventoryName;
        // create shared preference object

        sharedPreference = context.getSharedPreferences(PreferName, Context.MODE_PRIVATE);
       // sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);


        shared_data=  sharedPreference.getInt(PreferKey,0);


        this.inventoryDate = inventoryDate;
        this.item = item;
        zeroLevelElements = new ArrayList<String>();
        zeroLevelElementCode= new ArrayList<String>();
        dbHolder= new ArrayList<TableInventories>();

        dbClient = new db_DesireOutput(context);
        dbMain = new db_MainStatic(context);
        dbPdfView = new db_PdfView(context);
    }

    @Override
    protected void onPreExecute() {
        bold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        title = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
        normal = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        small = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

        Date date = new Date();
        CharSequence time = DateFormat.format("MM-dd-yy hh-mm-ss", date.getTime());

        db_DesireOutput dbHelper1 = new db_DesireOutput(context);
        dbHelper1.Open();
        Cursor cur12;
        cur12 = dbHelper1.fetchDetailsById(propertyID);
        String HouseNo ="";
        String PostCode ="";
        while(cur12.moveToNext()){
            HouseNo =cur12.getString(cur12.getColumnIndex("BP01_HOUSE_NO"));
            PostCode = cur12.getString(cur12.getColumnIndex("BP01_POST_CODE"));
            FURNISHED = cur12.getString(cur12.getColumnIndex("BP01_FURNISHED"));

            if(FURNISHED.trim().equalsIgnoreCase("true")){
                TITLE_FURNISHED = "furnished";
            }
            else{
                TITLE_FURNISHED = "Unfurnished";
            }
        }
        cur12.close();
        dbHelper1.close();

       // String currentInventoryDateandTime=inventoryDate.replace("-","")+":"+getCurrentTime();
        String currentInventoryDateandTime=inventoryDate.replace("-","")+"-"+PostCode.trim()+"-"+HouseNo.trim();

        fileName = "INV-" + currentInventoryDateandTime + ".pdf";

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Creating pdf.");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Nullable
    @Override
    protected String doInBackground(String... strings) {

        try {

            dbClient.Open();
            Cursor c;
            c = dbClient.fetchZerolvtDataCode(propertyID, inventoryDate.replaceAll("-",""));

            while(c.moveToNext()){
                if(!zeroLevelElementCode.contains(c.getString(0)))
                    zeroLevelElementCode.add(c.getString(0));
            }
            c.close();

            dbClient.close();

            dbMain.Open();
            for(int r =0; r<zeroLevelElementCode.size();r++){
                Cursor c11;
                c11 = dbMain.getInventoryDetailsforzero(zeroLevelElementCode.get(r));

                while(c11.moveToNext()){
                    zeroLevelElements.add(c11.getString(0));
                }
                c11.close();

            }
            dbMain.Close();

            /*
            * @condition: use for restrict more then 3 pdf
            * */
            if (shared_data != 0) {
                if (shared_data == CHECK_AFTER_PURCHASE_PDF_COUNT) {
                   // Toast.makeText(context,"You can generate only 3 pdf",Toast.LENGTH_SHORT).show();
                 /*   Intent t = new Intent(activity_ViewSaveData.this, activity_InApptesting.class).putExtra("Step","1");
                    startActivity(t);*/
                }else{
                    generatePDF();

                }
            }
            else{
                generatePDF();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        try {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (shared_data != 0) {
                                if (shared_data == CHECK_AFTER_PURCHASE_PDF_COUNT) {

                                    sendMail();

                             /*       File pdfDir = new File(Utility.getFilePath(fileName));

                                    if(pdfDir.exists()){
                                        new AlertDialog.Builder(context)
                                                .setMessage("You have exceeded the maximum limit if 3 Inventory pdf. Do you want to use an existing Inventory pdf.")
                                                .setCancelable(false)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        sendMail();

                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                    else{
                                        //You have exceeded the maximum limit if 3 Inventory pdf. Please purchase more to continue.

                                        new AlertDialog.Builder(context)
                                                .setMessage("You have exceeded the maximum limit if 3 Inventory pdf. Please purchase more to continue.")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();

                                                    }
                                                })
                                               .show();
                                    }*/


                                }else{
                                    sendMail();

                                }
                            }
                            else{
                                sendMail();
                            }

                            callFxnForUpdateValueSP();
                        }
                    },500);


        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private void callFxnForUpdateValueSP() {
        // editor use for edit value in shared preference database.
        SharedPreferences.Editor editor = sharedPreference.edit();

        // comment for check user create 3 or more pdf in application
        if (shared_data != 0) {
            // shared preference having details
            if (shared_data == CHECK_AFTER_PURCHASE_PDF_COUNT) {
                // Toast.makeText(context,"You can generate only 3 pdf",Toast.LENGTH_SHORT).show();
                //  nothing do
            }else{
                editor.putInt(PreferKey,shared_data+1);
                editor.commit();

            }
        }else {
            // shared preference not having details
            editor.putInt(PreferKey,1);
            editor.commit();
        }
    }

    public void sendMail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, "receiver_email_address");
        email.putExtra(Intent.EXTRA_SUBJECT, "INVENTORY-PDF");
        email.putExtra(Intent.EXTRA_TEXT, "Inventory Table");
        File pdfDir = new File(Utility.getFilePath(fileName));
        Uri uri = Uri.fromFile(pdfDir);
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("application/pdf");
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(email);
    }

    private void generatePDF() {
        try {

            Document document = new Document(PageSize.A4, 0, 0, 0, 0);
            document.setMargins(0,0,20,0);// set margin of each page
            File file = new File(Utility.getFilePath(fileName));
            if (file.exists()) {
                file.delete();
            }
            writer = PdfWriter.getInstance(document, new FileOutputStream(Utility.getFilePath(fileName)));
            HeaderFooter event = new HeaderFooter();
            writer.setBoxSize("art", new Rectangle(0, 54, 559, 788));
            writer.setPageEvent(event);

            document.open();
            addMetaData(document);
            addTitleAndUpperPart(document);
            createMainTable(document);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMetaData(@NonNull Document document) {
        document.addTitle("ABCInventory");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Phoenix");
        document.addCreator("Phoenix");
    }

    private void addTitleAndUpperPart(@NonNull Document document) {
        try {
            Paragraph ttl = new Paragraph("Household Inventory - "+TITLE_FURNISHED, title);
            ttl.setAlignment(Element.ALIGN_CENTER);
            document.add(ttl);
            ttl.setSpacingAfter(6);
            ttl.setSpacingBefore(50);

            PdfPTable table = new PdfPTable(1);
            table.setSpacingBefore(8);
            table.setSpacingAfter(8);
            Paragraph para = new Paragraph("", normal);
            PdfPCell cell = new PdfPCell(para);
            cell.setBackgroundColor(new BaseColor(98, 36, 35));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setFixedHeight(5);
            table.addCell(cell);
            document.add(table);


            Paragraph name = new Paragraph("Property Address: " + inventoryName, normal);
            name.setAlignment(Element.ALIGN_CENTER);
            document.add(name);
            name.setSpacingAfter(6);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(inventoryDate);
            CharSequence time = DateFormat.format("dd MMMM yyyy", d.getTime());
            Paragraph date = new Paragraph("Date : " + time, normal);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);
            date.setSpacingAfter(6);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class HeaderFooter extends PdfPageEventHelper {
        @NonNull
        Phrase[] header = new Phrase[2];

        int pagenumber;

        public void onOpenDocument(PdfWriter writer, Document document) {
            header[0] = new Phrase("Movie history");
        }


        public void onChapter(PdfWriter writer, Document document,
                              float paragraphPosition, @NonNull Paragraph title) {
            header[1] = new Phrase(title.getContent());
            pagenumber = 1;
        }

        public void onStartPage(PdfWriter writer, Document document) {
            pagenumber++;
        }

        public void onEndPage(@NonNull PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_LEFT, new Phrase(inventoryName),rect.getLeft() + 12 , rect.getBottom() - 50, 0);// set botttom margin
            ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_RIGHT, new Phrase(String.format("page %d", pagenumber)),rect.getRight()+20, rect.getBottom() - 50, 0);
        }
    }

    private void addImage(@NonNull Document document, int drawable) {
        Drawable d = context.getResources().getDrawable(drawable);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        byte[] bitmapData = stream.toByteArray();
        try {
            Image image = Image.getInstance(bitmapData);
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void   createMainTable(@NonNull Document document) {
        try {
            for (int position = 0; position < zeroLevelElements.size(); position++) {

                /**********initiate value******************/

                /**********initiate value******************/

                // getting zero level item
                String zeroItem = zeroLevelElements.get(position);

                /************Add zero lvl item at border in pdf*********************/
                int cellSize[] = {9, 18, 35, 35, 78};
                PdfPTable table = new PdfPTable(175);
                table.setWidthPercentage(95f);

                if (position == 0) {
                    table.setSpacingBefore(10);
                }

                addCell(table, zeroItem, 175, new BaseColor(192, 192, 192), bold, Element.ALIGN_CENTER);
                /************Add zero lvl item at border in pdf*********************/

                /************use for set column caption*********************/
                for (int i = 0; i < titles.length; i++) {
                    addCell(table, titles[i], cellSize[i], BaseColor.WHITE, bold, Element.ALIGN_CENTER);
                }
                /************use for set column caption*********************/

               /********************use for getting value of zero level item and add into pdf table*************************/
                dbPdfView.Open();
                Cursor cf;
                cf = dbPdfView.fetchDataforFirstlvl(propertyID, zeroItem,inventoryDate.replaceAll("-",""));
                int valueCount = cf.getCount();
                int i=1;
                while(cf.moveToNext()){

                    String photos ="";
                    String firstlvlData = "";
                    String ThirdLvlData = "";
                    String SecondLvlData ="";
                    String photosSplit[] = new String[3];
                    List<String> photos1= new ArrayList<String>();

                    /****** use for set position value in pdf***********/
                    String pos;

                    if(valueCount >1){
                        pos = ""+(position+1)+"."+i;
                    }
                    else{
                        pos =""+(position+1);
                    }
                    /****** use for set position value in pdf***********/

                    /*************getting details from database**********/
                    firstlvlData = cf.getString(4);
                    ThirdLvlData = cf.getString(6);
                    SecondLvlData= cf.getString(5);
                    photos = cf.getString(1);
                    /*************getting details from database**********/

                    /**
                     * use: use for append flag if having multiple value.
                     * Date: 05-April-2017
                     */
                    CallFxnForUpdateDataWithMultiFlag callFxnForUpdateDataWithMultiFlag = new CallFxnForUpdateDataWithMultiFlag(cf, firstlvlData, SecondLvlData).invoke();
                    firstlvlData = callFxnForUpdateDataWithMultiFlag.getFirstlvlData();
                    SecondLvlData = callFxnForUpdateDataWithMultiFlag.getSecondLvlData();

                    //////////////////////////////////////////////////

                    String data = "";

                    if(ThirdLvlData.length()>0)
                        ThirdLvlData = ThirdLvlData.substring(0,ThirdLvlData.length()-1);

                    if(photos.length()>0)
                        photos= photos.substring(0,photos.length()-1);

                    try{

                        ThirdLvlData = ThirdLvlData.replace("|", ",");
                        String thirdData[] = ThirdLvlData.split(",");
                        String values[]= new String[thirdData.length];

                        if(ThirdLvlData.length()>0){
                            if(ThirdLvlData.contains(",")) {
                                for (int y = 0; y < thirdData.length; y++) {
                                    values[y] = thirdData[y];
                                }
                            }
                            else{
                                values[0]=ThirdLvlData;
                            }
                        }

                        if(values.length>1){
                            for(int t = 0; t<values.length;t++){
                                data = data+" "+values[t]+"\n";//data+"• "+values[t]+"\n"
                            }
                        }
                        else{
                            data= " "+values[0];//data= "• "+values[0];
                        }

                        if(photos.length()>1) {
                            photos = photos.replace("|", ",");
                            photosSplit = photos.split(",");
                        }
                        else{
                            photosSplit[0]=photos;
                            photosSplit[1]=photos;
                            photosSplit[2]=photos;
                        }

                        String photo = "";
                        String comment = "";


                        if (TextUtils.isEmpty(firstlvlData)) {
                            firstlvlData = "";
                        }

                        if (photosSplit != null) {
                            for (int x = 0; x < photosSplit.length; x++) {
                                String path = photosSplit[x];
                                if (!TextUtils.isEmpty(path)) {
                                    photos1.add(path);
                                }
                            }
                        }

                        /************use for getting data from comment*******/
                        for (int k = 0; k < thirdData.length; k++) {
                            String text = thirdData[k];
                            text = text.replace("Comment Box", "Comment");
                            if (text.contains("Comment")) {
                                String cmnts[] = text.split(":");
                                if (cmnts.length > 1) {
                                    comment = cmnts[1];
                                }
                            }
                        }
                        /************use for getting data from comment*******/

                        /******add second lvl data before data if exist***********/
                        if(SecondLvlData.length()>0){
                            data = SecondLvlData +"\n"+data;
                        }

                        addCell(table, pos, cellSize[0], BaseColor.WHITE, normal, Element.ALIGN_CENTER);
                        addCell(table, firstlvlData, cellSize[1], BaseColor.WHITE, normal, Element.ALIGN_CENTER);
                        addCell(table, data, cellSize[2], BaseColor.WHITE, small, Element.ALIGN_LEFT);
                        addCell(table, comment, cellSize[3], BaseColor.WHITE, normal, Element.ALIGN_CENTER); // comment

                        if (photos1.size() > 0) {
                            try {
                                PdfPCell cell = new PdfPCell();
                                Image image = Image.getInstance(Utility.getImageCombinedByte(photos1));
                                image.setAlignment(Image.MIDDLE | Image.TEXTWRAP);
                                image.setBorder(Image.NO_BORDER);
                                image.setBorderWidth(1);
                                cell.setColspan(cellSize[4]);
                                cell.addElement(image);
                                table.addCell(cell);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                addCell(table, "", cellSize[4], BaseColor.WHITE, normal, Element.ALIGN_LEFT);
                            }
                        } else {
                               addCell(table, "", cellSize[4], BaseColor.WHITE, normal, Element.ALIGN_LEFT);
                        }

                    }
                    catch (Exception e){

                    }
                    i++;
                }
                cf.close();
                dbPdfView.close();
                /********************use for getting value of zero level item and add into pdf table*************************/

                // use for add data into table
                document.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCell(@NonNull PdfPTable table, String text, int colSpan, BaseColor color, Font font, int alignment) {
        Paragraph para = new Paragraph(text, font);
        PdfPCell cell = new PdfPCell(para);
        cell.setBackgroundColor(color);
        cell.setColspan(colSpan);
        cell.setHorizontalAlignment(alignment);
        cell.setPaddingBottom(7);
        //table.setKeepTogether(true);
        table.addCell(cell);

    }
    //@function: use for getting current time
    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");

        return df.format(c.getTime());
    }

    private class CallFxnForUpdateDataWithMultiFlag {
        private Cursor cf;
        private String firstlvlData;
        private String secondLvlData;

        public CallFxnForUpdateDataWithMultiFlag(Cursor cf, String firstlvlData, String secondLvlData) {
            this.cf = cf;
            this.firstlvlData = firstlvlData;
            this.secondLvlData = secondLvlData;
        }

        public String getFirstlvlData() {
            return firstlvlData;
        }

        public String getSecondLvlData() {
            return secondLvlData;
        }

        public CallFxnForUpdateDataWithMultiFlag invoke() {
            String CheckMultiID = cf.getString(cf.getColumnIndex(dbPdfView.MULTI_S_FLAG));
            String Level = cf.getString(cf.getColumnIndex(dbPdfView.LEVEL));
            String FirstLevelName= cf.getString(cf.getColumnIndex(dbPdfView.FIRSTLEVELITEM));
            String SecondLevelName=  cf.getString(cf.getColumnIndex(dbPdfView.SECONDLEVELITEM));
            String MultiFlag_ID=  cf.getString(cf.getColumnIndex(MULTIID));

            if(Level.equalsIgnoreCase("2")){
                if(CheckMultiID.equalsIgnoreCase("Y")){
                    firstlvlData= FirstLevelName = FirstLevelName+": "+MultiFlag_ID;
                }
            }
            else if(Level.equalsIgnoreCase("3")){
                if(CheckMultiID.equalsIgnoreCase("Y")){
                    secondLvlData =SecondLevelName = SecondLevelName+": "+MultiFlag_ID;
                }

            }
            return this;
        }
    }
}