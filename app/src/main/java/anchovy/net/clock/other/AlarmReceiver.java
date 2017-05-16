package anchovy.net.clock.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by DarKnight98 on 5/15/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm started!", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Alarm stopped!", Toast.LENGTH_SHORT).show();
    }
}
