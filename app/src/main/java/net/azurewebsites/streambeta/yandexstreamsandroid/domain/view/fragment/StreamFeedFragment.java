package net.azurewebsites.streambeta.yandexstreamsandroid.domain.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.azurewebsites.streambeta.yandexstreamsandroid.R;
import net.azurewebsites.streambeta.yandexstreamsandroid.core.view.BaseFragment;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.interactor.mapped.StreamFeedItemModel;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.presenter.instancestate.StreamFeedPresenterInstanceState;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.presenter.interfaces.StreamFeedPresenter;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.router.MainRouter;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.view.adapter.StreamFeedRecyclerAdapter;
import net.azurewebsites.streambeta.yandexstreamsandroid.domain.view.interfaces.StreamFeedView;

import java.util.List;

import butterknife.BindView;

public class StreamFeedFragment extends BaseFragment implements StreamFeedView {
    @BindView(R.id.et_toolbar)
    EditText etToolbar;
    @BindView(R.id.iv_qr_button)
    ImageView ivQrButton;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_search_hint)
    TextView tvSearchHint;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.rv_stream_list)
    RecyclerView rvStreamList;

    StreamFeedPresenter presenter;

    private StreamFeedRecyclerAdapter recyclerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_list;
    }

    @Override
    public String getPresenterId() {
        return "stream_feed";
    }

    @Override
    public void onStart() {
        presenter = getPresenter(StreamFeedPresenter.class);
        presenter.setView(this);
        presenter.setRouter((MainRouter) getActivity());
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.saveInstanceState(
                new StreamFeedPresenterInstanceState(
                        etToolbar.getText().toString(),
                        rvStreamList.getVerticalScrollbarPosition(),
                        recyclerAdapter.getData()
                ));
        presenter.setView(null);
    }

    @Override
    public View setupView(View view) {
        setupRecyclerView();
        ivQrButton.setOnClickListener(v -> presenter.onQrButtonPressed());
        setupSearchBar();
        return view;
    }

    @Override
    public void loadData() {
        presenter.onQueryStringModified("");
    }

    private void setupRecyclerView() {
        recyclerAdapter = new StreamFeedRecyclerAdapter(getContext());
        rvStreamList.setAdapter(recyclerAdapter);
        rvStreamList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSearchBar() {
        etToolbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    ivSearchIcon.setVisibility(View.VISIBLE);
                    tvSearchHint.setVisibility(View.VISIBLE);
                    tvToolbar.setText(R.string.title_search_toolbar);
                } else {
                    ivSearchIcon.setVisibility(View.INVISIBLE);
                    tvSearchHint.setVisibility(View.INVISIBLE);
                    tvToolbar.setText(R.string.title_search_toolbar_results);
                }
                if (presenter != null)
                    presenter.onQueryStringModified(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void scrollToPosition(int position) {
        rvStreamList.scrollToPosition(position);
    }

    @Override
    public void appendFeed(List<StreamFeedItemModel> feed) {
        recyclerAdapter.appendDataWithNotify(feed);
    }

    @Override
    public void resetFeed() {
        recyclerAdapter.clear();
    }

    public static StreamFeedFragment newInstance() {
        return new StreamFeedFragment();
    }

    @Override
    public void setQueryText(String query) {
        etToolbar.setText(query);
    }
}