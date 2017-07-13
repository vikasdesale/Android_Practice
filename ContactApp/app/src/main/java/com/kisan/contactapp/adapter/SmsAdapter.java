package com.kisan.contactapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisan.contactapp.R;
import com.kisan.contactapp.database.model.SmsSent;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kisan.contactapp.util.ContactsUtil.manipulateDateFormat;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsHolder> {


    private Cursor mCursor;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public SmsAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    private void postItemClick(SmsHolder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
        }
    }

    @Override
    public SmsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_sms, parent, false);

        return new SmsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SmsHolder holder, int position) {
        SmsSent sms = getItem(position);
        holder.contactName.setText(mContext.getString(R.string.name) + sms.getFirstname() + " " + sms.getLastname());
        holder.contactOtp.setText(mContext.getString(R.string.your_otp) + sms.getOtp());
        holder.contactDate.setText(mContext.getString(R.string.otp) + manipulateDateFormat(sms.getDate_time()));
        
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }


    public SmsSent getItem(int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid item position requested");
        }

        return new SmsSent(mCursor);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
    }

    /* Callback for list item click events */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }

    /* ViewHolder for each task item */
    public class SmsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.contact_name)
        TextView contactName;
        @BindView(R.id.contact_date)
        TextView contactDate;
        @BindView(R.id.contact_otp)
        TextView contactOtp;

        public SmsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            postItemClick(this);
        }
    }
}
