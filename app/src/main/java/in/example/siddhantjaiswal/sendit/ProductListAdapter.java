package in.example.siddhantjaiswal.sendit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProductList;

    public ProductListAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    public int getCount() {
        return this.mProductList.size();
    }

    public Object getItem(int position) {
        return this.mProductList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(this.mContext, R.layout.item_product_list, null);
        TextView tvPrice = (TextView) v.findViewById(R.id.tv_price);
        TextView tvDescription = (TextView) v.findViewById(R.id.tv_description);
        TextView td = (TextView) v.findViewById(R.id.id1);
        ((TextView) v.findViewById(R.id.tv_name)).setText(((Product) this.mProductList.get(position)).getName());
        tvPrice.setText(String.valueOf(((Product) this.mProductList.get(position)).getPrice()));
        tvDescription.setText(((Product) this.mProductList.get(position)).getDescription());
        td.setText(((Product) this.mProductList.get(position)).getId());
        return v;
    }
}
