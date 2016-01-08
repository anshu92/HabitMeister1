package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.app.Fragment;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

import android.view.View.OnClickListener;
import android.widget.Toast;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements FragmentOne.update_conn_status,OnClickListener {

<<<<<<< Updated upstream
	public static final boolean ON_PHONE = false;
	public static long[] event_timestamps = new long[10];
	public static int event_counter = 0;
    public static  boolean start_recording = false;
    public static  boolean updating_data = false;
    public static  boolean updating_conn = false;
    public static ConnectionState connection_state = null;
    public static ConnectionState previous_connection_state = null;
    public static int[] epoch_num = new int[10];
=======
	public static final boolean ON_PHONE = true;
>>>>>>> Stashed changes



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

            final ConnectionState current = p.getCurrentConnectionState();
            connection_state = current;
            previous_connection_state = p.getPreviousConnectionState();
            status = p.getPreviousConnectionState().toString() +
                    " -> " + current;
            final String full = "Muse " + p.getSource().getMacAddress() +
                    " " + status;

            Log.i("Muse Headband", full);
            Activity activity = activityRef.get();
            // UI thread is used here only because we need to update
            // TextView values. You don't have to use another thread, unless
            // you want to run disconnect() or connect() from connection packet
            // handler. In this case creating another thread is required.
            if (activity != null && updating_conn) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView statusText =
                                (TextView) findViewById(R.id.con_status);

						if(statusText != null)
                        	statusText.setText(status);

                       TextView museVersionText =
                               (TextView) findViewById(R.id.version);
                        if (current == ConnectionState.CONNECTED) {
                            MuseVersion museVersion = muse.getMuseVersion();
                            String version = museVersion.getFirmwareType() +
                                 " - " + museVersion.getFirmwareVersion() +
                                 " - " + Integer.toString(
                                    museVersion.getProtocolVersion());
                            museVersionText.setText(version);
                        } else {
                            museVersionText.setText(R.string.undefined);
                        }
                    }
                });
            }
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
						updateEeg(p.getValues(),p);

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
					updateBlinkJaw(blink, clench_count == 5);
				}

			}

			private void updateAccelerometer(final ArrayList<Double> data) {
				Activity activity = activityRef.get();
				if (activity != null && updating_data ) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView acc_x = (TextView) findViewById(R.id.acc_x);
							TextView acc_y = (TextView) findViewById(R.id.acc_y);
							TextView acc_z = (TextView) findViewById(R.id.acc_z);
							if (!(acc_x == null || acc_y == null || acc_z == null)) {
								acc_x.setText(String.format(
										"%6.2f", data.get(Accelerometer.FORWARD_BACKWARD.ordinal())));
								acc_y.setText(String.format(
										"%6.2f", data.get(Accelerometer.UP_DOWN.ordinal())));
								acc_z.setText(String.format(
										"%6.2f", data.get(Accelerometer.LEFT_RIGHT.ordinal())));
							}
						}
					});
				}
			}

			private void updateEeg(final ArrayList<Double> data, final MuseDataPacket p) {
				Activity activity = activityRef.get();
				if (activity != null && updating_data) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView tp9 = (TextView) findViewById(R.id.eeg_tp9);
							TextView fp1 = (TextView) findViewById(R.id.eeg_fp1);
							TextView fp2 = (TextView) findViewById(R.id.eeg_fp2);
							TextView tp10 = (TextView) findViewById(R.id.eeg_tp10);
							if(!(tp9 == null || fp1 == null || fp2 == null || tp10 == null)) {

								tp9.setText(String.format(
										"%6.2f", data.get(Eeg.TP9.ordinal())));
								fp1.setText(String.format(
										"%6.2f", data.get(Eeg.FP1.ordinal())));
								fp2.setText(String.format(
										"%6.2f", data.get(Eeg.FP2.ordinal())));
								tp10.setText(String.format(
										"%6.2f", data.get(Eeg.TP10.ordinal())));
							}
						}
					});

					if(ON_PHONE) {
						if (start_recording) {
							fileWriter.addDataPacket(1, p);
							event_timestamps[event_counter++] = p.getTimestamp();

							// It's library client responsibility to flush the buffer,
							if (fileWriter.getBufferedMessagesSize() > 8096)
								fileWriter.flush();
						}
					}
				}
			}

			private void updateAlphaRelative(final ArrayList<Double> data) {
				Activity activity = activityRef.get();
				if (activity != null && updating_data ) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView elem1 = (TextView) findViewById(R.id.elem1);
							TextView elem2 = (TextView) findViewById(R.id.elem2);
							TextView elem3 = (TextView) findViewById(R.id.elem3);
							TextView elem4 = (TextView) findViewById(R.id.elem4);
							if(!(elem1 == null || elem2 == null || elem3 == null || elem4 == null)) {

								elem1.setText(String.format(
										"%6.2f", data.get(Eeg.TP9.ordinal())));
								elem2.setText(String.format(
										"%6.2f", data.get(Eeg.FP1.ordinal())));
								elem3.setText(String.format(
										"%6.2f", data.get(Eeg.FP2.ordinal())));
								elem4.setText(String.format(
										"%6.2f", data.get(Eeg.TP10.ordinal())));
							}
						}
					});
				}
			}

			private void updateMellow(final ArrayList<Double> data) {
				Activity activity = activityRef.get();

				if (activity != null && updating_data ) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView mellow = (TextView) findViewById(R.id.mellow);
							double n = 100 * data.get(0);
							if(mellow != null) {
								mellow.setText(String.format(
										"%4.2f", n));
							}

						}
					});
				}

			}

			private void updateConcentration(final ArrayList<Double> data) {
				Activity activity = activityRef.get();
				if (activity != null && updating_data ) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView concentration = (TextView) findViewById(R.id.concentration);
							if (concentration != null) {
								double n = 100 * data.get(0);
								concentration.setText(String.format(
										"%4.2f", n));
							}

						}
					});
				}
			}

			private void updateBlinkJaw( final boolean blink, final boolean jaw) {
				Activity activity = activityRef.get();

				if (activity != null && updating_data) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {

							TextView blink_t = (TextView) findViewById(R.id.blink);
							TextView jaw_t = (TextView) findViewById(R.id.jaw);

							if(blink_t != null && jaw_t != null) {


								blink_t.setText(String.format(
										"%b", blink));
								jaw_t.setText(String.format(
										"%b", jaw));
							}

						}
					});
				}
			}


			public void setFileWriter(MuseFileWriter fileWriter) {
			   this.fileWriter  = fileWriter;
			}
		}

    final String[] data = {"Home", "Raw EEG", "Plot","Calibration", "SVM"};
    final String[] fragments = {
            "com.example.asahoo264.habitmeister1.FragmentOne",
            "com.example.asahoo264.habitmeister1.FragmentTwo",
            "com.example.asahoo264.habitmeister1.FragmentThree",
            "com.example.asahoo264.habitmeister1.FragmentFour",
			"com.example.asahoo264.habitmeister1.FragmentFive"
    };
    public Muse muse = null;
    public static ConnectionListener connectionListener = null;
    public static DataListener dataListener = null;
    private boolean dataTransmission = true;
    private MuseFileWriter fileWriter = null;
    private int clench_count = 0;
	public static String status = null;
    public static ArrayAdapter<String> adapterArray_status = null;


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
						android.app.FragmentTransaction tx = getFragmentManager().beginTransaction();
						tx.replace(R.id.mainframe, android.app.Fragment.instantiate(MainActivity.this, fragments[pos]));
						tx.commit();
					}
				});
				drawer.closeDrawer(navList);
			}
		});
        android.app.FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.mainframe, android.app.Fragment.instantiate(MainActivity.this, fragments[0]));
        tx.commit();

		File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
		if(ON_PHONE) {
			fileWriter = MuseFileFactory.getMuseFileWriter(
					new File(dir, "new_muse_file.muse"));
			Log.i("Muse Headband", "libmuse version=" + LibMuseVersion.SDK_VERSION);
			Log.i("Muse Headband", dir.getPath());
			fileWriter.addAnnotationString(1, "MainActivity onCreate");
			dataListener.setFileWriter(fileWriter);
		}
    }



    @Override
    public void onClick(View v) {
        Spinner musesSpinner = (Spinner) findViewById(R.id.muses_spinner);
        if (v.getId() == R.id.refresh) {
            MuseManager.refreshPairedMuses();
            List<Muse> pairedMuses = MuseManager.getPairedMuses();
            List<String> spinnerItems = new ArrayList<String>();
            for (Muse m: pairedMuses) {
                String dev_id = m.getName() + "-" + m.getMacAddress();
                Log.i("Muse Headband", dev_id);
                spinnerItems.add(dev_id);
            }
            ArrayAdapter<String> adapterArray = new ArrayAdapter<String> (
                    this, android.R.layout.simple_spinner_item, spinnerItems);
            adapterArray_status = adapterArray;
            musesSpinner.setAdapter(adapterArray);
        }
        else if (v.getId() == R.id.connect) {
            List<Muse> pairedMuses = MuseManager.getPairedMuses();
            if (pairedMuses.size() < 1 ||
                    musesSpinner.getAdapter().getCount() < 1) {
                Log.w("Muse Headband", "There is nothing to connect to");
            }
            else {

                muse = pairedMuses.get(musesSpinner.getSelectedItemPosition());
                ConnectionState state = muse.getConnectionState();
				if (state == ConnectionState.CONNECTED ||
                        state == ConnectionState.CONNECTING) {
                    Log.w("Muse Headband",
                            "doesn't make sense to connect second time to the same muse");
                    return;
                }
                configureLibrary();
				if(ON_PHONE) {
					fileWriter.open();
					fileWriter.addAnnotationString(1, "Connect clicked");
				}
                /**
                 * In most cases libmuse native library takes care about
                 * exceptions and recovery mechanism, but native code still
                 * may throw in some unexpected situations (like bad bluetooth
                 * connection). Print all exceptions here.
                 */
                try {
                    muse.runAsynchronously();
                } catch (Exception e) {
                    Log.e("Muse Headband", e.toString());
                }
            }
        }
        else if (v.getId() == R.id.disconnect) {
            if (muse != null) {
                /**
                 * true flag will force libmuse to unregister all listeners,
                 * BUT AFTER disconnecting and sending disconnection event.
                 * If you don't want to receive disconnection event (for ex.
                 * you call disconnect when application is closed), then
                 * unregister listeners first and then call disconnect:
                 * muse.unregisterAllListeners();
                 * muse.disconnect(false);
                 */
                muse.disconnect(true);
				if(ON_PHONE) {
					fileWriter.addAnnotationString(1, "Disconnect clicked");
					fileWriter.flush();
					fileWriter.close();
				}
            }
        }

    }




    @Override
    public void setonclickstuff(Button refreshButton, Button connectButton, Button disconnectButton) {
        disconnectButton.setOnClickListener(this);
        connectButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);

    }


    public void configureLibrary() {

        muse.registerConnectionListener(connectionListener);
        muse.registerDataListener(dataListener,
                MuseDataPacketType.ACCELEROMETER);
        muse.registerDataListener(dataListener,
                MuseDataPacketType.EEG);
        muse.registerDataListener(dataListener,
                MuseDataPacketType.ALPHA_RELATIVE);
        muse.registerDataListener(dataListener,
                MuseDataPacketType.ARTIFACTS);
        muse.registerDataListener(dataListener,
                MuseDataPacketType.BATTERY);
		muse.registerDataListener(dataListener,
                MuseDataPacketType.MELLOW);
        muse.registerDataListener(dataListener,
				MuseDataPacketType.CONCENTRATION);
        muse.setPreset(MusePreset.PRESET_14);
        muse.enableDataTransmission(dataTransmission);

    }

	/*
     * Simple example of getting data from the "*.muse" file
     */
	private void playMuseFile(String name) {
		File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
		File file = new File(dir, name);
		final String tag = "Muse File Reader";
		if (!file.exists()) {
			Log.w(tag, "file doesn't exist");
			return;
		}
		MuseFileReader fileReader = MuseFileFactory.getMuseFileReader(file);
		while (fileReader.gotoNextMessage()) {
			MessageType type = fileReader.getMessageType();
			int id = fileReader.getMessageId();
			long timestamp = fileReader.getMessageTimestamp();
			Log.i(tag, "type: " + type.toString() +
					" id: " + Integer.toString(id) +
					" timestamp: " + String.valueOf(timestamp));
			switch(type) {
				case EEG: case BATTERY: case ACCELEROMETER: case QUANTIZATION:
					MuseDataPacket packet = fileReader.getDataPacket();
					Log.i(tag, "data packet: " + packet.getPacketType().toString());
					break;
				case VERSION:
					MuseVersion version = fileReader.getVersion();
					Log.i(tag, "version" + version.getFirmwareType());
					break;
				case CONFIGURATION:
					MuseConfiguration config = fileReader.getConfiguration();
					Log.i(tag, "config" + config.getBluetoothMac());
					break;
				case ANNOTATION:
					AnnotationData annotation = fileReader.getAnnotation();
					Log.i(tag, "annotation" + annotation.getData());
					break;
				default:
					break;
			}
		}
	}


	public ConnectionListener getConnectionListener() {

			return connectionListener;
		}

		public DataListener getDataListener() {

			return dataListener;
		}



	}

