package npike.net.wearfart;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import net.npike.android.util.util.LogWrap;

import java.io.IOException;
import java.util.Random;

public class DataLayerListenerService extends WearableListenerService {

    private MediaPlayer mPlayer = null;
    private Random mRandom;

    public static void startService(Context context) {
        context.startService(new Intent(context, DataLayerListenerService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogWrap.l();

        mRandom = new Random();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if("/FART".equals(messageEvent.getPath())) {
            // launch some Activity or do anything you like
            LogWrap.l();

//            try {
//                if (mPlayer.isPlaying()) {
//                    mPlayer.stop();
//                    mPlayer.release();
//                    mPlayer = new MediaPlayer();
//                }
//
//                AssetFileDescriptor descriptor = getAssets().openFd("fart_01.mp3");
//                mPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//                descriptor.close();
//
//                mPlayer.prepare();
//                mPlayer.setVolume(1f, 1f);
//                mPlayer.setLooping(true);
//                mPlayer.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            int randomFart = mRandom.nextInt(3);

            int fartResource = R.raw.fart_01;

            if (randomFart == 1) {
                fartResource = R.raw.fart_02;
            } else if (randomFart == 2) {
                fartResource = R.raw.fart_03;
            }

            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), fartResource);
            mediaPlayer.start();

        }
    }
}