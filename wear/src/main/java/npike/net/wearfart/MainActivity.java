package npike.net.wearfart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import net.npike.android.util.util.LogWrap;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTextView;
    private CircledImageView mCircledImageView;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mCircledImageView = (CircledImageView) stub.findViewById(R.id.circledImageView);

                mCircledImageView.setImageResource(R.drawable.ic_launcher);
                mCircledImageView.setCircleBorderColor(getResources().getColor(R.color.test));
                mCircledImageView.setCircleColor(Color.GREEN);
                mCircledImageView.setCircleHidden(false);
                mCircledImageView.setCircleRadius(5);
                mCircledImageView.setOnClickListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        LogWrap.l();


        final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient);
        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult result) {
                final List<Node> nodes = result.getNodes();
                LogWrap.l("Found "+nodes.size() + " nodes.");
                for (int i=0; i<nodes.size(); i++) {
                    final Node node = nodes.get(i);

                    // You can just send a message
                    mCircledImageView.setProgress(5);
                    Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), "/FART", null);

                    // or you may want to also check check for a result:
                    // final PendingResult<SendMessageResult> pendingSendMessageResult = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), "/MESSAGE", null);
                    // pendingSendMessageResult.setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                    //      public void onResult(SendMessageResult sendMessageResult) {
                    //          if (sendMessageResult.getStatus().getStatusCode()==WearableStatusCodes.SUCCESS) {
                    //              // do something is successed
                    //          }
                    //      }
                    // });
                }
            }
        });
    }
}
