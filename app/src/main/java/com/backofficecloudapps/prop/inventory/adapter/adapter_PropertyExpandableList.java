package com.backofficecloudapps.prop.inventory.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Phoenix
 */
public class adapter_PropertyExpandableList extends BaseExpandableListAdapter {

    private Activity mContext;
    private Map<String,List<String>> mChildParentData;
    private List<String> mGroupParentData;
    public static LinearLayout linLayout;

    /**
     * Instantiates a new Adapter property expandable list.
     *
     * @param mContext         the m context
     * @param mChildParentData the m child parent data
     * @param mGroupParentData the m group parent data
     */
    public adapter_PropertyExpandableList(Activity mContext, Map<String, List<String>> mChildParentData, List<String> mGroupParentData) {
        this.mContext = mContext;
        this.mChildParentData = mChildParentData;
        this.mGroupParentData = mGroupParentData;
    }

    @Override
    public int getGroupCount() {
        return mGroupParentData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildParentData.get(mGroupParentData.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupParentData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildParentData.get(mGroupParentData.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true; // false to true... change
    }

    @Nullable
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, @Nullable View convertView, ViewGroup parent) {

        String Name = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,null);
        }

        linLayout = (LinearLayout) convertView.findViewById(R.id.groupId);

        TextView item = (TextView) convertView.findViewById(R.id.PropertyMain);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(Name);

        if(isExpanded)
        {
            //You can Apply your code here like below code for change Background when group expanded
            linLayout.setBackgroundColor(mContext.getResources().getColor(R.color.custom));
        }
        else
        {
            //You can Apply your code here like below code for change Background when group not expanded(collapsed)
            linLayout.setBackgroundColor(mContext.getResources().getColor(R.color.expandable_background));
        }

        return convertView;
    }

    @Nullable
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, @Nullable View convertView, ViewGroup parent) {

        // fetch value of child according to parent
        final String Name = (String) getChild(groupPosition, childPosition);

        // use for inflate child layout
        LayoutInflater inflater = mContext.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        // fetch value of Property Name from Array
        String[] PropertyName = mContext.getResources().getStringArray(R.array.expand_child_info);

        TextView PropName = (TextView) convertView.findViewById(R.id.prop_Name);
        TextView PropValue = (TextView) convertView.findViewById(R.id.prop_Value);

        if(PropertyName[childPosition].equals("Furnished")){
            String data="";
            if(Name.equalsIgnoreCase("true")){
                data ="Yes";
            }
            else{
                data ="No";
            }

            PropValue.setText(data);
        }
        else{
            PropValue.setText(Name);
        }


        try {
            PropName.setText(""+PropertyName[childPosition]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Sets child view.
     *
     * @param groupPosition the group position
     * @param childItems    the child items
     * @param property      the property
     */
    public void setChildView(int groupPosition, @Nullable List<String> childItems, @NonNull List<String> property) {
        if (childItems != null && !childItems.isEmpty()) {
                 this.mChildParentData.put(property.get(groupPosition) + "", childItems);
        }
    }


}
