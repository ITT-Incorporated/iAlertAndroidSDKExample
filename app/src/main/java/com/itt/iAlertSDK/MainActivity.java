package com.itt.iAlertSDK;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSensorManager;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSystemInfoAndUIDelegate;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3OTAFirmwareUpdateCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.Protocols.ITTScanningProtocol;
import com.itt.ialert.sensor.model.ITTLEPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class MainActivity extends AppCompatActivity implements ITTSystemInfoAndUIDelegate {

    public static ITTSensorManager sensorManager;
    public static HashMap<String, ITTLEPacket> mPacketMap = new HashMap<>();

    private ScanArrayAdapter mAdapter;
    private ListView list;
    private List<Sensor> items = new ArrayList<Sensor>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ScanArrayAdapter(this, items);
        list = (ListView) findViewById(R.id.list);
        list.setClickable(true);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent commands = new Intent(MainActivity.this, CommandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("device", items.get(position).getDeviceName());
                bundle.putString("address", items.get(position).getAddress());
                commands.putExtras(bundle);
                startActivity(commands);
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            initiateScanning();
        }

    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                } else {
                    initiateScanning();
                }
                break;
        }
    }




    private void initiateScanning() {
        ITTSensorManager manager = null;
        try {
            ITTSensorManager.ITTIoTDataEnvironment ittIoTDataEnvironment = ITTSensorManager.ITTIoTDataEnvironment.ITT_LSP_VIETNAM_Dev;
            String licenseJWTEncrypted = "";   // provided by ITT
            manager = ITTSensorManager.getInstance(this, ittIoTDataEnvironment, Looper.getMainLooper(), licenseJWTEncrypted, true, this);    // The thread corresponding to this Looper will receive the Scanning callback below
            sensorManager = manager;
        } catch (ClassNotFoundException e) {           // You must have embedded in your application the AWS Android SDK iot and core packages (https://github.com/aws/aws-sdk-android), in order to be able to use the ITT-iAlert SDK
            e.printStackTrace();
        }

        if (manager != null) {
            manager.addScanDelegate(new ITTScanningProtocol() {
                @Override
                public void ittSensorManagerDidDetectIAlert(ITTSensorManager sensorManager, ScanResult result, ITTLEPacket packet) {
                    String deviceName = result.getScanRecord().getDeviceName();               // result.getScanRecord() and deviceName are always not null here.
                    if (deviceName == null) deviceName = packet.getiAlertID();
                    BluetoothDevice device = result.getDevice();
                    String name = device == null ? null : device.getName();                // equals deviceName
                    String address = device.getAddress();
                    final Boolean hasPacketChanged = !mPacketMap.containsValue(packet);
                    mPacketMap.put(deviceName, packet);
                    if (hasPacketChanged) {
                        rediscover(packet, deviceName, address);
                    }
                }




                @Override           // packet is not null only for iA3 sensors with Rev3 Bootloader firmware
                public void ittSensorManagerDidDetectIAlert3InBootloaderMode(ITTSensorManager sensorManager, String deviceName, ScanResult result, ITTLEPacket packet, boolean isOTAHandledByTheSDK) {
                    BluetoothDevice device = result.getDevice();
                    ITTLEPacket packet1 = packet != null ? packet : mPacketMap.get(deviceName);       // must be not null from a previous scan callback above
                    if (!isOTAHandledByTheSDK && packet1 != null) {
                        boolean canUpdateFirmware = ITTiAlert3OTAFirmwareUpdateCommand.canUpdateFirmware(packet1);
                        if (canUpdateFirmware) {
                            try {
                                ITTiAlert3OTAFirmwareUpdateCommand command = new ITTiAlert3OTAFirmwareUpdateCommand(MainActivity.this, packet1, true, false);
                                ((ITTiAlert3OTAFirmwareUpdateCommand)command).setRestartFailedOTA(true);
                                command.didDiscoverIAlert3BootloaderPacket(deviceName, device, packet1);   // executes the OTA update
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


            });

            manager.startScan(false);
        }
    }




    // Invoked only by the SDK during initialization, if Bluetooth is not enabled on this device.
    // The user, must implement this method by first enabling Bluetooth and then invoking the following SDK method.
    @Override
    public void enableBluetooth() {
        //TODO: enable Bluetooth here
        ITTSensorManager.getITTSensorManager().initializeBleScanParametersAfterBluetoothIsEnabled();
    }


    // Invoked only by the SDK when publishing messages to the AWS IoT, for checking if there is currently a Network connection.
    // The user, must implement this method by providing a suitable implementation for the current Android device type.
    @Override
    public boolean isNetworkReachable() {
        //TODO: implement properly
        return true;
    }




    // Invoked only by the SDK when publishing messages to the AWS IoT, for including the device's IP address in the message envelope.
    // The user, must implement this method by providing a suitable implementation for the current Android device type.
    @Override
    public String getIPAddress() {
        //TODO: implement properly
        return "";
    }







    void rediscover(ITTLEPacket packet, String deviceName, String address) {
        Sensor sensor = new Sensor(packet, deviceName, address);
        int index = sensorExists(deviceName);
        if (index == -1) {
            items.add(sensor);
        } else {
            items.set(index, sensor);
        }
        mAdapter.notifyDataSetChanged();
        //mAdapter.clear();
        //mAdapter.addAll();
    }



    int sensorExists(String deviceName) {
        int index = -1;
        if (!items.isEmpty()) {
            for (Sensor sensor : items) {
                if (sensor.getDeviceName().equals(deviceName)) {
                    index = items.indexOf(sensor);
                    break;
                }
            }
        }
        return index;
    }





    public class ScanArrayAdapter extends ArrayAdapter {
        public List<Sensor> items;
        private Context context;
        private LayoutInflater inflater;

        public ScanArrayAdapter(Context context, List<Sensor> items) {
            super(context, R.layout.main_list_item, items);
            this.items = items;
            this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            ViewHolder viewHolder;
            if (v == null) {
                v = inflater.inflate(R.layout.main_list_item, parent, false);
                viewHolder = new ViewHolder(v, position);
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)v.getTag();
                viewHolder.setPosition(position);
            }

            Sensor sensor = items.get(position);

            /*final View help = v;
            TextView tag = (TextView) v.findViewById(R.id.sensorTag);
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    ((AbsListView) list).performItemClick(help, position, position);
                }
            });

            TextView macAddress = (TextView) v.findViewById(R.id.sensorMacAddress);
            macAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    ((AbsListView) list).performItemClick(help, position, position);
                }
            });

            tag.setText(sensor.getTag());
            macAddress.setText(sensor.getDeviceName());*/

            ITTLEPacket.SensorType sensorType = sensor.getPeripheralType();
            viewHolder.tag.setText(sensor.getTag());
            viewHolder.macAddress.setText(sensor.getDeviceName());
            viewHolder.sensorTypeLabel.setVisibility(sensorType.ordinal() == ITTLEPacket.SensorType.PressureSensor.ordinal() ? View.VISIBLE : View.INVISIBLE);
            return v;
        }


        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return this.items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }




        private class ViewHolder {
            //View view;
            int position;
            TextView sensorTypeLabel;
            TextView tag;
            TextView macAddress;

            ViewHolder(final View view, int position) {
                //this.view = view;
                this.position = position;
                this.sensorTypeLabel = (TextView) view.findViewById(R.id.sensorTypeLabel);
                this.tag = (TextView) view.findViewById(R.id.sensorTag);
                this.tag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        ((AbsListView) list).performItemClick(view, ViewHolder.this.position, ViewHolder.this.position);
                    }
                });

                this.macAddress = (TextView) view.findViewById(R.id.sensorMacAddress);
                this.macAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        ((AbsListView) list).performItemClick(view, ViewHolder.this.position, ViewHolder.this.position);
                    }
                });
            }


            void setPosition(int position) {
                this.position = position;
            }
        }
    }






    public class Sensor {
        ITTLEPacket packet;
        String deviceName;      // mac address of sensor
        String address;         // hardware address of sensor

        public Sensor(ITTLEPacket packet, String deviceName, String address) {
            this.packet = packet;
            this.deviceName = deviceName;
            this.address = address;
        }



        public ITTLEPacket.SensorType getPeripheralType() {
            return packet.getPeripheralType();
        }



        public String getTag() {
            return packet.getTag();
        }

        public String getDeviceName() {
            return deviceName;
        }

        public String getAddress() {
            return address;
        }
    }


}
