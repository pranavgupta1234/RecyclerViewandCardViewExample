package devexchanges.info.recycleviewandcardview;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Friend> friends;
    private Activity activity;

    public RecyclerAdapter(Activity activity, List<Friend> friends) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.name.setText(friends.get(position).getName());
        viewHolder.job.setText(friends.get(position).getJob());

        if (friends.get(position).isGender()) {
            viewHolder.imageView.setImageResource(R.mipmap.male);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.female);
        }
        //set on click listener for each element
        viewHolder.container.setOnClickListener(onClickListener(position));
    }

    private void setDataToView(TextView name, TextView job, ImageView genderIcon, int position) {
        name.setText(friends.get(position).getName());
        job.setText(friends.get(position).getJob());
        if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.mipmap.male);
        } else {
            genderIcon.setImageResource(R.mipmap.female);
        }
    }

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView job = (TextView) dialog.findViewById(R.id.job);
                ImageView icon = (ImageView) dialog.findViewById(R.id.image);

                setDataToView(name, job, icon, position);

                dialog.show();
            }
        };
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView job;
        private View container;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            job = (TextView) view.findViewById(R.id.job);
            container = view.findViewById(R.id.card_view);
        }
    }
}