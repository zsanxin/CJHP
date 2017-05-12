package in.srain.cube.views.ptr.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

/**
 * @author huqiu.lhq
 */
public class LoadMoreExpandableListViewContainer extends LoadMoreContainerBase {

    private ExpandableListView mListView;

    public LoadMoreExpandableListViewContainer(Context context) {
        super(context);
    }

    public LoadMoreExpandableListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addFooterView(View view) {
        Log.i("result","addFooterView");
        mListView.addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        mListView.removeFooterView(view);
    }

    @Override
    protected AbsListView retrieveAbsListView() {
        mListView = (ExpandableListView) getChildAt(0);
        return mListView;
    }
}
