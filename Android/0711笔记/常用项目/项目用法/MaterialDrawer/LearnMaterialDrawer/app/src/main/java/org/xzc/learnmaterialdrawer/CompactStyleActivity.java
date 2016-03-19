package org.xzc.learnmaterialdrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikepenz.materialdrawer.AccountHeaderBuilder;

public class CompactStyleActivity extends BaseActivity {
	@Override
	protected AccountHeaderBuilder createAccountHeaderBuilder() {
		AccountHeaderBuilder b = super.createAccountHeaderBuilder();
		b.withCompactStyle(true);
		return b;
	}
}
