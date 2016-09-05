package git.feicui.com.gitdroid.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import git.feicui.com.gitdroid.gank.model.GankItem;

/**
 * Created by Administrator on 16-9-2.
 */
public class GankResult {



    private List<String> category;

    private boolean error;

    private Result result;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{

        @SerializedName("Android")
        private List<GankItem> androidItems;

        public List<GankItem> getAndroidItems() {
            return androidItems;
        }
    }
}
