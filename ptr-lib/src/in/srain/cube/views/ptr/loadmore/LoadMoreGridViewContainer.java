package in.srain.cube.views.ptr.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * @author huqiu.lhq
 */
public class LoadMoreGridViewContainer extends LoadMoreContainerBase {

    private GridViewWithHeaderAndFooter mListView;

    public LoadMoreGridViewContainer(Context context) {
        super(context);
    }

    public LoadMoreGridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addFooterView(View view) {
        mListView.addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        mListView.removeFooterView(view);
    }

    @Override
    protected AbsListView retrieveAbsListView() {
        mListView = (GridViewWithHeaderAndFooter ) getChildAt(0);
        return mListView;
    }
}
