package retrofit2.sample;

import android.app.Activity;
import android.os.Bundle;
import com.github.*;
import rx.Observable;
import rx.functions.*;

public class DetailActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        GitHub github = GitHub.create();
        github.contributors("yongjhih", "retrofit2").forEach(new Action1<Contributor>() {
            @Override public void call(Contributor contributor) {
                android.util.Log.d("retrofit2", contributor.login());
            }
        });
    }
}
