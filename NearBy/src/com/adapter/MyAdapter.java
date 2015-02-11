package com.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.image.utils.ImageLoader;
import com.model.PlaceDetail;
import com.nearby.app.R;
import com.nearby.app.ui.ActivityDetail;

/**
 * Created by amol on 10/02/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
		implements OnClickListener {
	ArrayList<PlaceDetail> mDataset = new ArrayList<PlaceDetail>();
	ImageLoader imageLoader;

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyAdapter(ArrayList<PlaceDetail> myDataset) {
		mDataset = myDataset;

	}

	// Create new views (invoked by the layout manager)
	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.card_layout, parent, false);
		// set the view's size, margins, paddings and layout parameters
		v.setPadding(10, 10, 10, 10);
		// initialize image loader
		imageLoader = new ImageLoader(parent.getContext());

		ViewHolder vh = new ViewHolder(v);

		// set click listener
		v.setOnClickListener(this);
		// set tag
		v.setTag(vh);

		return vh;

	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		PlaceDetail data = mDataset.get(position);
		holder.mTextView.setText(data.getName());

		imageLoader.DisplayImage(data.getIconUrl(), holder.mIconView);
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public TextView mTextView;
		public ImageView mIconView;

		public ViewHolder(View v) {
			super(v);
			mTextView = (TextView) v.findViewById(R.id.textViewIconName);
			mIconView = (ImageView) v.findViewById(R.id.imageViewIcon);
		}
	}

	@Override
	public void onClick(View v) {
		ViewHolder holder = (ViewHolder) v.getTag();
		int position = holder.getPosition();
		PlaceDetail item = mDataset.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("DETAILS", item);

		Toast.makeText(v.getContext(), item.getName(), Toast.LENGTH_LONG)
				.show();
		// slide from right to left
		((Activity) v.getContext()).overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
		v.getContext().startActivity(
				new Intent(v.getContext().getApplicationContext(),
						ActivityDetail.class).putExtras(bundle));
	}
}