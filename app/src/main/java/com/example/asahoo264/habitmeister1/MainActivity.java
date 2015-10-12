package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.interaxon.libmuse.Accelerometer;
import com.interaxon.libmuse.AnnotationData;
import com.interaxon.libmuse.ConnectionState;
import com.interaxon.libmuse.Eeg;
import com.interaxon.libmuse.LibMuseVersion;
import com.interaxon.libmuse.MessageType;
import com.interaxon.libmuse.Muse;
import com.interaxon.libmuse.MuseArtifactPacket;
import com.interaxon.libmuse.MuseConfiguration;
import com.interaxon.libmuse.MuseConnectionListener;
import com.interaxon.libmuse.MuseConnectionPacket;
import com.interaxon.libmuse.MuseDataListener;
import com.interaxon.libmuse.MuseDataPacket;
import com.interaxon.libmuse.MuseDataPacketType;
import com.interaxon.libmuse.MuseFileFactory;
import com.interaxon.libmuse.MuseFileReader;
import com.interaxon.libmuse.MuseFileWriter;
import com.interaxon.libmuse.MuseManager;
import com.interaxon.libmuse.MusePreset;
import com.interaxon.libmuse.MuseVersion;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity {
	final String[] data = {"Home", "Raw EEG", "Plot","Calibration"};
	final String[] fragments = {
			"com.example.asahoo264.habitmeister1.FragmentOne",
			"com.example.asahoo264.habitmeister1.FragmentTwo",
			"com.example.asahoo264.habitmeister1.FragmentThree",
			"com.example.asahoo264.habitmeister1.FragmentFour"
			};
	private Muse muse = null;
	public static ConnectionListener connectionListener = null;
	public static DataListener dataListener = null;
	private boolean dataTransmission = true;
	private MuseFileWriter fileWriter = null;
	private int clench_count = 0;

	public MainActivity() {
		// Create listeners and pass reference to activity to them
		WeakReference<Activity> weakActivity =
				new WeakReference<Activity>(this);

		connectionListener = new ConnectionListener(weakActivity);
		dataListener = new DataListener(weakActivity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.navbarlayout, data);


		final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		final ListView navList = (ListView) findViewById(R.id.drawer);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
				drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
						tx.replace(R.id.mainframe, Fragment.instantiate(MainActivity.this, fragments[pos]));
						tx.commit();
					}
				});
				drawer.closeDrawer(navList);
			}
		});
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.mainframe, Fragment.instantiate(MainActivity.this, fragments[0]));
		tx.commit();


	}

	/**
	 * Connection listener updates UI with new connection status and logs it.
	 */
	class ConnectionListener extends MuseConnectionListener {

		final WeakReference<Activity> activityRef;

		ConnectionListener(final WeakReference<Activity> activityRef) {
			this.activityRef = activityRef;
		}

		@Override
		public void receiveMuseConnectionPacket(MuseConnectionPacket p) {
			FragmentOne fragmentone = (FragmentOne)getSupportFragmentManager().findFragmentById(R.id.mainframe);
			if(fragmentone != null && fragmentone.isVisible()){
						fragmentone.update_status(p);}
			FragmentTwo fragmenttwo = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
			if(fragmenttwo != null && fragmenttwo.isVisible()){
				fragmenttwo.update_status(p);}

		}
	}

		/**
		 * Data listener will be registered to listen for: Accelerometer,
		 * Eeg and Relative Alpha bandpower packets. In all cases we will
		 * update UI with new values.
		 * We also will log message if Artifact packets contains "blink" flag.
		 * DataListener methods will be called from execution thread. If you are
		 * implementing "serious" processing algorithms inside those listeners,
		 * consider to create another thread.
		 */
		class DataListener extends MuseDataListener {

			final WeakReference<Activity> activityRef;
			private MuseFileWriter fileWriter;

			DataListener(final WeakReference<Activity> activityRef) {
				this.activityRef = activityRef;
			}

			@Override
			public void receiveMuseDataPacket(MuseDataPacket p) {
				switch (p.getPacketType()) {
					case EEG:
						updateEeg(p.getValues());
						break;
					case ACCELEROMETER:
						updateAccelerometer(p.getValues());
						break;
					case ALPHA_RELATIVE:
						updateAlphaRelative(p.getValues());
						break;
					case BATTERY:
						//fileWriter.addDataPacket(1, p);

						// It's library client responsibility to flush the buffer,
						// otherwise you may get memory overflow.
						//if (fileWriter.getBufferedMessagesSize() > 8096)
						//  fileWriter.flush();
						break;
					case MELLOW:
						updateMellow(p.getValues());
						break;
					case CONCENTRATION:
						updateConcentration(p.getValues());
						break;

					default:
						break;
				}
			}

			@Override
			public void receiveMuseArtifactPacket(MuseArtifactPacket p) {
				boolean ison = p.getHeadbandOn();
				boolean blink = p.getBlink();
				boolean jawclench = p.getJawClench();

				if (ison && blink) {
					Log.i("Artifacts", "blink");
				}
				if (ison && jawclench) {
					Log.i("Artifacts", "jaw clench");
					clench_count = clench_count + 1;
					if (clench_count == 7)
						clench_count = 1;
				}
				if (ison) {
					updateBlinkJaw(blink,clench_count==5);
				}

			}

			private void updateAccelerometer(final ArrayList<Double> data) {
				FragmentTwo fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
				Activity activity = activityRef.get();
				if (activity != null && fragment != null && fragment.isVisible()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView acc_x = (TextView) findViewById(R.id.acc_x);
							TextView acc_y = (TextView) findViewById(R.id.acc_y);
							TextView acc_z = (TextView) findViewById(R.id.acc_z);
							acc_x.setText(String.format(
									"%6.2f", data.get(Accelerometer.FORWARD_BACKWARD.ordinal())));
							acc_y.setText(String.format(
									"%6.2f", data.get(Accelerometer.UP_DOWN.ordinal())));
							acc_z.setText(String.format(
									"%6.2f", data.get(Accelerometer.LEFT_RIGHT.ordinal())));
						}
					});
				}
			}

			private void updateEeg(final ArrayList<Double> data) {
				FragmentTwo fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
				Activity activity = activityRef.get();
				if (activity != null && fragment != null && fragment.isVisible()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView tp9 = (TextView) findViewById(R.id.eeg_tp9);
							TextView fp1 = (TextView) findViewById(R.id.eeg_fp1);
							TextView fp2 = (TextView) findViewById(R.id.eeg_fp2);
							TextView tp10 = (TextView) findViewById(R.id.eeg_tp10);
							tp9.setText(String.format(
									"%6.2f", data.get(Eeg.TP9.ordinal())));
							fp1.setText(String.format(
									"%6.2f", data.get(Eeg.FP1.ordinal())));
							fp2.setText(String.format(
									"%6.2f", data.get(Eeg.FP2.ordinal())));
							tp10.setText(String.format(
									"%6.2f", data.get(Eeg.TP10.ordinal())));
						}
					});
				}
			}

			private void updateAlphaRelative(final ArrayList<Double> data) {
				FragmentTwo fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
				Activity activity = activityRef.get();
				if (activity != null && fragment != null && fragment.isVisible()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView elem1 = (TextView) findViewById(R.id.elem1);
							TextView elem2 = (TextView) findViewById(R.id.elem2);
							TextView elem3 = (TextView) findViewById(R.id.elem3);
							TextView elem4 = (TextView) findViewById(R.id.elem4);
							elem1.setText(String.format(
									"%6.2f", data.get(Eeg.TP9.ordinal())));
							elem2.setText(String.format(
									"%6.2f", data.get(Eeg.FP1.ordinal())));
							elem3.setText(String.format(
									"%6.2f", data.get(Eeg.FP2.ordinal())));
							elem4.setText(String.format(
									"%6.2f", data.get(Eeg.TP10.ordinal())));
						}
					});
				}
			}

			private void updateMellow(final ArrayList<Double> data) {
				FragmentTwo fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
				Activity activity = activityRef.get();
				if (activity != null && fragment != null && fragment.isVisible()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView mellow = (TextView) findViewById(R.id.mellow);
							double n = 100 * data.get(0);
							mellow.setText(String.format(
									"%4.2f", n));

						}
					});
				}

			}

			private void updateConcentration(final ArrayList<Double> data) {
				FragmentTwo fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
				Activity activity = activityRef.get();
				if (activity != null && fragment != null && fragment.isVisible()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView concentration = (TextView) findViewById(R.id.concentration);

							double n = 100 * data.get(0);
							concentration.setText(String.format(
									"%4.2f", n));

						}
					});
				}
			}

			private void updateBlinkJaw( final boolean blink, final boolean jaw) {
				FragmentTwo fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.mainframe);
				Activity activity = activityRef.get();

				if (activity != null && fragment != null && fragment.isVisible()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {

							TextView blink_t = (TextView) findViewById(R.id.blink);
							TextView jaw_t = (TextView) findViewById(R.id.jaw);

							blink_t.setText(String.format(
									"%b", blink));
							jaw_t.setText(String.format(
									"%b", jaw));

						}
					});
				}
			}


			//public void setFileWriter(MuseFileWriter fileWriter) {
			//   this.fileWriter  = fileWriter;
			//}
		}


		public Muse getmuse() {

			return muse;
		}

		public ConnectionListener getConnectionListener() {

			return connectionListener;
		}

		public DataListener getDataListener() {

			return dataListener;
		}
	}
