package net.azurewebsites.streambeta.yandexstreamsandroid.domain.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.azurewebsites.streambeta.yandexstreamsandroid.R;
import net.azurewebsites.streambeta.yandexstreamsandroid.core.view.BaseRecyclerAdapter;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.interactor.mapped.StreamFeedItemModel;
import net.azurewebsites.streambeta.yandexstreamsandroid.util.GlideUtils;

import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tetawex on 29.07.2017.
 */

public class StreamFeedRecyclerAdapter
        extends BaseRecyclerAdapter<StreamFeedItemModel, StreamFeedRecyclerAdapter.ViewHolder> {

    Consumer<StreamFeedItemModel> onClickListener;

    public StreamFeedRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindSingleItem(ViewHolder holder, StreamFeedItemModel item) {
        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvViews.setText(item.getAudience());
        GlideUtils.loadImageIntoView(context, holder.ivAvatar, item.getImageUrl(), R.color.colorToolbarBackground);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.accept(item);
                }
            }
        });
    }

    @Override
    protected ViewHolder createVH(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycleritem_stream;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_views)
        TextView tvViews;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
