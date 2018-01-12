/*Taken from: 
	- Android - Keep ListView's item highlighted once one has been clicked
	- http://stackoverflow.com/questions/9281000/android-keep-listviews-item-highlighted-once-one-has-been-clicked
*/
package com.canon.vi.newsarticlereader;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> testList;
    private int selectedIndex;
    private int selectedColor = Color.parseColor("#1b1b1b");

    public TestAdapter(Context ctx, ArrayList<String> testList)
    {
        this.context = ctx;
        this.testList = testList;
        selectedIndex = -1;
    }

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return testList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return testList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private class ViewHolder
    {
        TextView tv;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     * 
     * I found this great explanation of what's going on in the getView override below
     * http://stackoverflow.com/questions/12400338/explanation-of-the-getview-method-of-an-arrayadapter
     * The convertView is the view of a row that left the screen(so it isn't the last view returned by 
     * the getView method). For example, the list is first shown, in this case convertView is null, 
     * no row view was previously built and left the screen. If you scroll down, row 0 will leave the screen
     * (will not be visible anymore), when that happens the ListView may choose to keep that view in a cache
     * to later use it (this makes sense, as the rows of a ListView generally have the same layout with 
     * only the data being different). The reason to keep some views in a cache and later use them is 
     * because the getView method could be called a lot of times(each time the user scrolls up/down and new 
     * rows appear on the screen). If each time the row view would need to be recreated this would have 
     * resulted in a lot of objects being created which is something to avoid. In your getView method you 
     * would check convertView to see if it is null. If it's null then you must build a new row view and 
     * populate it with data, if it isn't null, the ListView has offered you a previous view. Having this 
     * previous view means you don't need to build a new row layout, instead you must populate it with the 
     * correct data, as that cached view has the old data still attached to it(you would see a lot of 
     * questions on stackoverflow where users ask why the rows of their ListView are duplicating when they 
     * scroll down).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        ViewHolder holder;
        if(convertView == null)
        {
//            vi = LayoutInflater.from(context).inflate(R.layout.test_list_item, null);
            vi = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
            holder = new ViewHolder();

            holder.tv = (TextView) vi;

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) vi.getTag();
        }

        if(selectedIndex!= -1 && position == selectedIndex)
        {
            holder.tv.setBackgroundColor(Color.BLACK);
        }
        else
        {
            holder.tv.setBackgroundColor(selectedColor);
        }
//        holder.tv.setText("" + (position + 1) + " " + testList.get(position).getTestText());
        holder.tv.setText("" + (position + 1) + " " + testList.get(position).toString());

        return vi;
    }

}