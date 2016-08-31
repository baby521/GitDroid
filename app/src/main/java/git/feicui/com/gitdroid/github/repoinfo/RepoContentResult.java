package git.feicui.com.gitdroid.github.repoinfo;

/**
 * 仓库详情显示完成
 *
 * 获取readme响应结果
 *
 * 作者：yuanchao on 2016/8/1 0001 15:31
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoContentResult {
//    {
//        "encoding": "base64",
//            "content": "encoded content ..."
//    }
    private String content;
    private String encoding;

    public String getContent() {
        return content;
    }

    public String getEncoding() {
        return encoding;

    }
}
