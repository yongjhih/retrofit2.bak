/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.json.JSONObject;
import org.json.JSONException;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.github.*;

import rx.Observable;
import rx.functions.*;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    @Test
    public void testUrl() {
        GitHub github = GitHub.create();

        List<String> contributors = github.contributorsDynamic("https://api.github.com/repos/yongjhih/retrofit2/contributors").map(new Func1<Contributor, String>() {
            @Override public String call(Contributor contributor) {
                System.out.println(contributor.login());
                return contributor.login();
            }
        }).toList().toBlocking().single();
        assertTrue(contributors.contains("yongjhih"));
        assertTrue(contributors.size() > 1);
    }

    @Test
    public void testWithoutBaseUrl() {
        GitHub github = GitHub.create();
        List<String> contributors = github.contributorsWithoutBaseUrl("yongjhih", "retrofit2").map(new Func1<Contributor, String>() {
            @Override public String call(Contributor contributor) {
                System.out.println(contributor.login());
                return contributor.login();
            }
        }).toList().toBlocking().single();
        assertTrue(contributors.contains("yongjhih"));
        assertTrue(contributors.size() > 1);
    }

    @Test
    public void testBaseUrl() {
        GitHub github = GitHub.create();
        List<String> contributors = github.contributors("yongjhih", "retrofit2").map(new Func1<Contributor, String>() {
            @Override public String call(Contributor contributor) {
                System.out.println(contributor.login());
                return contributor.login();
            }
        }).toList().toBlocking().single();
        assertTrue(contributors.contains("yongjhih"));
        assertTrue(contributors.size() > 1);
    }
}
