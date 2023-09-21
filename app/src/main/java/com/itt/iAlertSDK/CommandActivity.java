package com.itt.iAlertSDK;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTAcquisitionRateWriteCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTAlarmWakeUpIntervalWriteCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3GetFluxFFTCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3GetLastAlarmFFTCommand;
import com.itt.ialert.sensor.model.ITTAdvertisingPacketInterface;
import com.itt.ialert.sensor.model.ITTAxialValues;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTBLECommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetAlarmFFTsAndTWFsCommand;
import com.itt.ialert.sensor.model.ITTPressureAcquisitionRateRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureEraseCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureGetAcquisitionRateCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureGetThresholdsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureGetTrendsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureGetTypeCommand;
import com.itt.ialert.sensor.model.ITTPressureSensorAdvertisingPacketInterface;
import com.itt.ialert.sensor.model.ITTPressureMutableThreshold;
import com.itt.ialert.sensor.model.ITTPressureSensorTypeRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureSetAcquisitionRateCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureSetThresholdAlarmHighCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureSetThresholdWarningHighCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureTagReadCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureTagWriteCommand;
import com.itt.ialert.sensor.model.ITTPressureThresholds;
import com.itt.ialert.sensor.model.ITTPressureTrendRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTPressureZeroPressureCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTBLECompoundCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTCountersReadCommand;
import com.itt.ialert.sensor.model.ITTDeviceStatsRecord;
import com.itt.ialert.sensor.model.ITTFFTRecord;
import com.itt.ialert.sensor.model.ITTFFTTWFResponseRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetAlarmFFTsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetAlarmTWFsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetDeviceStatsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetLiveReadingCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetLongFFTCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetRunningThresholdCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetShortFFTCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetThresholdsHighCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetThresholdsLowCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTGetWeeklyTrendsCommand;
import com.itt.ialert.sensor.model.ITTHourlyTrendRecord;
import com.itt.ialert.sensor.model.ITTLEPacket;
import com.itt.ialert.sensor.model.ITTLiveReadingRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTRequestNewLongFFTCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTRequestNewShortFFTCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSensorManager;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSetRunningThresholdCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSetThresholdsHighCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSetThresholdsLowCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTSystemParamsReadCommand;
import com.itt.ialert.sensor.model.ITTSystemParamsRecord;
import com.itt.ialert.sensor.model.ITTTWFRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTTagReadCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTTagWriteCommand;
import com.itt.ialert.sensor.model.ITTWeeklyTrendRecord;
import com.itt.ialert.sensor.model.ITTiAlert3AdvPacketDataImpl;
import com.itt.ialert.sensor.model.ITTiAlert3AdvertisingPacket;
import com.itt.ialert.sensor.model.ITTiAlert3AdvertisingPacketInterface;
import com.itt.ialert.sensor.model.ITTiAlert3FFTTWFRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3GetAlarmFFTsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3GetManualFFTCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3GetTrendsCommand;
import com.itt.ialert.sensor.model.ITTiAlert3FluxFFTTWFRecord;
import com.itt.ialert.sensor.model.ITTiAlert3MutableSystemStructure;
import com.itt.ialert.sensor.model.ITTiAlert3SystemStructure;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3SystemStructureReadCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlert3SystemStructureWriteCommand;
import com.itt.ialert.sensor.model.ITTiAlert3TrendRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertBaselineRequestCommand;
import com.itt.ialert.sensor.model.ITTiAlertCountersRecord;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertEraseCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertGetHourlyAlarmTrendsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertGetHourlyTrendsCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertGetTimestampsCommand;
import com.itt.ialert.sensor.model.ITTiAlertMutableThreshold;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertResetRuntimeCommand;
import com.itt.iAlertAndroidSDK.ITTSensorManager.ITTiAlertSleepCommand;
import com.itt.ialert.sensor.model.ITTiAlertThreshold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;



/**
 * Created by George.Gatt on 05/03/2018.
 */

public class CommandActivity extends AppCompatActivity {

    private CommandArrayAdapter mAdapter;
    private ListView list;
    private List<String> items = new ArrayList<String>();
    private String deviceName;
    private String address;
    private ITTSensorManager sensorManager;
    private ITTLEPacket packet;
    private BluetoothDevice device;
    private MenuItem executeItem;
    private List<Integer> selections = new ArrayList<>();

    private ITTBLECommand.ITTBLECommandStatusUpdate commandStatusHandler;
    private ITTBLECommand.ITTBLECommandCompletionHandler commandCompletionHandler;
    private ITTBLECommand.ITTBLECommandProgress commandProgressHandler;
    private ITTBLECommand command;
    private ITTBLECompoundCommand compoundCommand;
    private HashMap<Integer, ITTBLECommand> commandsMap = new HashMap<Integer, ITTBLECommand>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        setTitle("List of Bluetooth commands");
        final ActionBar abar = getSupportActionBar();
        abar.setDisplayHomeAsUpEnabled(true);

        deviceName = getIntent().getExtras().getString("device");
        address = getIntent().getExtras().getString("address");
        sensorManager = MainActivity.sensorManager;

        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bAdapter != null) {
            packet = MainActivity.mPacketMap.get(deviceName);
            device = bAdapter.getRemoteDevice(address);
        } else {
            finish();
            return;
        }

        commandStatusHandler = new ITTBLECommand.ITTBLECommandStatusUpdate() {
            @Override
            public void onITTBLECommandStatusChanged(ITTBLECommand.ITTBLECommandStatus commandStatus) {
                Log.d("Status changed", commandStatus.toString());
            }
        };

        commandCompletionHandler = new ITTBLECommand.ITTBLECommandCompletionHandler() {
            @Override
            public void onITTBLECommandCompleted(Object error, Object iAlertResponse) {
                if (error == null && iAlertResponse != null) {

                    if (compoundCommand != null) {
                        command = compoundCommand.getCurrentCommand();
                        if (command instanceof ITTiAlertGetTimestampsCommand) {
                            List<Long> validTimestamps = ((ITTiAlertGetTimestampsCommand)command).getiAlertResponse();
                            if (!validTimestamps.isEmpty()) {
                                long lastTimestamp = validTimestamps.get(validTimestamps.size()-1);
                                List<ITTBLECommand> pendingCommands = compoundCommand.getPendingCommands();
                                if (pendingCommands != null && !pendingCommands.isEmpty()) {
                                    for (ITTBLECommand command : pendingCommands) {
                                        if (command instanceof ITTGetAlarmFFTsCommand) {
                                            ((ITTGetAlarmFFTsCommand)command).setFromiAlertTimestamp(lastTimestamp);
                                            ((ITTGetAlarmFFTsCommand)command).setToiAlertTimestamp(lastTimestamp);
                                        }
                                        if (command instanceof ITTGetAlarmTWFsCommand) {
                                            ((ITTGetAlarmTWFsCommand)command).setFromiAlertTimestamp(lastTimestamp);
                                            ((ITTGetAlarmTWFsCommand)command).setToiAlertTimestamp(lastTimestamp);
                                        }
                                    }
                                }
                            }
                        }

                        if (command == null) {                               // Compound command completed
                            List<ITTBLECommand> finishedCommands = compoundCommand.getiAlertResponse();
                            Log.d("Commands completed: ", String.valueOf(finishedCommands.size()));
                        }
                    }

                    if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert2.ordinal()) {
                        if (command instanceof ITTiAlertGetHourlyAlarmTrendsCommand) {
                            List<ITTHourlyTrendRecord> trendRecords = ((ITTiAlertGetHourlyAlarmTrendsCommand) command).getiAlertResponse();
                            Log.d("Alarm Trends received: ", String.valueOf(trendRecords.size()));
                        } else if (command instanceof ITTTagReadCommand) {
                            String tag = ((ITTTagReadCommand) command).getTag();
                            Log.d("Tag read: ", tag);
                        } else if (command instanceof ITTiAlertGetHourlyTrendsCommand) {
                            List<ITTHourlyTrendRecord> trendRecords = ((ITTiAlertGetHourlyTrendsCommand) command).getiAlertResponse();
                            Log.d("Trends received: ", String.valueOf(trendRecords.size()));
                        } else if (command instanceof ITTTagWriteCommand) {
                            String tag = ((ITTTagWriteCommand) command).getTag();
                            Log.d("Tag set: ", tag);
                        } else if (command instanceof ITTCountersReadCommand) {
                            ITTiAlertCountersRecord record = ((ITTCountersReadCommand) command).getiAlertResponse();
                            Log.d("Counters read: ", String.valueOf(record.getiAlertTimestamp()));
                        } else if (command instanceof ITTGetAlarmFFTsCommand) {
                            List<ITTFFTRecord> fftRecords = ((ITTGetAlarmFFTsCommand) command).getiAlertResponse();
                            if (fftRecords != null && !fftRecords.isEmpty()) {
                                fftRecords.get(0).getFftDataX();
                            }
                            Log.d("Alarm FFTs received: ", String.valueOf(fftRecords.size()));
                        } else if (command instanceof ITTGetAlarmTWFsCommand) {
                            List<ITTTWFRecord> twfRecords = ((ITTGetAlarmTWFsCommand) command).getiAlertResponse();
                            Log.d("Alarm TWFs received: ", String.valueOf(twfRecords.size()));
                        } else if (command instanceof ITTGetDeviceStatsCommand) {
                            ITTDeviceStatsRecord statsRecord = ((ITTGetDeviceStatsCommand) command).getDeviceStats();
                            Log.d("Battery record read: ", String.valueOf(statsRecord.getConnectionsCount()));
                        } else if (command instanceof ITTGetLiveReadingCommand) {
                            ITTLiveReadingRecord liveRecord = ((ITTGetLiveReadingCommand) command).getiAlertResponse();
                            Log.d("Live Reading received: ", String.valueOf(liveRecord.getTemperature()));
                        } else if (command instanceof ITTGetLongFFTCommand) {
                            ITTFFTTWFResponseRecord longResponseRecord = ((ITTGetLongFFTCommand) command).getiAlertResponse();
                            Log.d("Long FFT/TWF received: ", String.valueOf(longResponseRecord.getiAlertTimestamp()));
                        } else if (command instanceof ITTGetRunningThresholdCommand) {
                            float runningThreshold = ((ITTGetRunningThresholdCommand) command).getRunningThreshold();
                            Log.d("Running Threshold read:", String.valueOf(runningThreshold));
                        } else if (command instanceof ITTGetShortFFTCommand) {
                            ITTFFTTWFResponseRecord shortResponseRecord = ((ITTGetShortFFTCommand) command).getiAlertResponse();
                            ITTTWFRecord twfRecord = shortResponseRecord.getTwfRecord();
                            shortResponseRecord.getFftRecord().getFftDataX();
                            ArrayList<Double> xValues = twfRecord.getxValues();
                            Log.d("Short FFT/TWF recvd: ", String.valueOf(shortResponseRecord.getiAlertTimestamp()));
                        } else if (command instanceof ITTGetThresholdsHighCommand) {
                            ITTiAlertThreshold thresholdHigh = ((ITTGetThresholdsHighCommand) command).getThresholdHigh();
                            Log.d("Threshold High read: ", String.valueOf(thresholdHigh.getTemperature()));
                        } else if (command instanceof ITTGetThresholdsLowCommand) {
                            ITTiAlertThreshold thresholdLow = ((ITTGetThresholdsLowCommand) command).getThresholdLow();
                            Log.d("Threshold High read: ", String.valueOf(thresholdLow.getTemperature()));
                        } else if (command instanceof ITTGetWeeklyTrendsCommand) {
                            List<ITTWeeklyTrendRecord> trendRecords = ((ITTGetWeeklyTrendsCommand) command).getiAlertResponse();
                            Log.d("Weekly Trends received:", String.valueOf(trendRecords.size()));
                        } else if (command instanceof ITTiAlertBaselineRequestCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Baseline command: ", String.valueOf(success));
                        } else if (command instanceof ITTiAlertEraseCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Erase command: ", String.valueOf(success));
                        } else if (command instanceof ITTiAlertResetRuntimeCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Reset runtime command: ", String.valueOf(success));
                        } else if (command instanceof ITTiAlertSleepCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Sleep command: ", String.valueOf(success));
                        } else if (command instanceof ITTRequestNewLongFFTCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Request new Long FFT: ", String.valueOf(success));
                        } else if (command instanceof ITTRequestNewShortFFTCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Request new Short FFT: ", String.valueOf(success));
                        } else if (command instanceof ITTSetRunningThresholdCommand) {
                            float runningThreshold = ((ITTSetRunningThresholdCommand) command).getRunningThreshold();
                            Log.d("Running Threshold set: ", String.valueOf(runningThreshold));
                        } else if (command instanceof ITTSetThresholdsHighCommand) {
                            ITTiAlertMutableThreshold thresholdHigh = ((ITTSetThresholdsHighCommand) command).getThresholdHigh();
                            Log.d("Threshold High set: ", String.valueOf(thresholdHigh.getTemperature()));
                        } else if (command instanceof ITTSetThresholdsLowCommand) {
                            ITTiAlertMutableThreshold thresholdLow = ((ITTSetThresholdsLowCommand) command).getThresholdLow();
                            Log.d("Threshold Low set: ", String.valueOf(thresholdLow.getTemperature()));
                        } else if (command instanceof ITTSystemParamsReadCommand) {
                            ITTSystemParamsRecord systemParams = ((ITTSystemParamsReadCommand) command).getiAlertResponse();
                            Log.d("systemParams read: ", String.valueOf(systemParams.getAcquisitionRateInMinutes()));
                        } else if (command instanceof ITTAcquisitionRateWriteCommand) {
                            int acquisitionRateInMinutes = ((ITTAcquisitionRateWriteCommand) command).getAcquisitionRateInMinutes();
                            Log.d("acquisitionRate set:", String.valueOf(acquisitionRateInMinutes));
                        } else if (command instanceof ITTAlarmWakeUpIntervalWriteCommand) {
                            int alarmWakeUpIntervalInMinutes = ((ITTAlarmWakeUpIntervalWriteCommand) command).getAlarmWakeUpIntervalInMinutes();
                            Log.d("alarmWakeUp set:", String.valueOf(alarmWakeUpIntervalInMinutes));
                        } else if (command instanceof ITTGetAlarmFFTsAndTWFsCommand) {
                            List<ITTFFTRecord> fftRecords = ((ITTGetAlarmFFTsAndTWFsCommand) command).getiAlertFFTResponse();
                            if (fftRecords != null && !fftRecords.isEmpty()) {
                                fftRecords.get(0).getFftDataX();
                            }
                            Log.d("Alarm FFTs received: ", String.valueOf(fftRecords.size()));
                            List<ITTTWFRecord> twfRecords = ((ITTGetAlarmFFTsAndTWFsCommand) command).getiAlertTWFResponse();
                            Log.d("Alarm TWFs received: ", String.valueOf(twfRecords.size()));
                        }

                    } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert3.ordinal()) {
                        if (command instanceof ITTiAlert3GetTrendsCommand) {
                            List<ITTiAlert3TrendRecord> trendRecords = ((ITTiAlert3GetTrendsCommand) command).getiAlertResponse();
                            if (trendRecords != null)
                                Log.d("Trends received: ", String.valueOf(trendRecords.size()));
                        } else if (command instanceof ITTiAlert3SystemStructureWriteCommand) {
                            ITTiAlert3SystemStructure writtenSystemStructure = ((ITTiAlert3SystemStructureWriteCommand) command).getiAlertResponse();
                            /*if (writtenSystemStructure != null)
                                Log.d("System Structure: ", writtenSystemStructure.toString());*/
                        } else if (command instanceof ITTiAlert3SystemStructureReadCommand) {
                            ITTiAlert3SystemStructure systemStructure = ((ITTiAlert3SystemStructureReadCommand) command).getiAlertResponse();
                            /*if (systemStructure != null)
                                Log.d("System Structure: ", systemStructure.toString());*/
                        } else if (command instanceof ITTiAlert3GetManualFFTCommand) {
                            ITTiAlert3FFTTWFRecord ffttwfRecord = ((ITTiAlert3GetManualFFTCommand) command).getiAlertResponse();
                            ArrayList<Double> xValues = ffttwfRecord.getTimeDomainDataX();
                            Log.d("FFT/TWF received: ", String.valueOf(ffttwfRecord.getiAlertTimestamp()));
                        } else if (command instanceof ITTiAlert3GetLastAlarmFFTCommand) {
                            ITTiAlert3FFTTWFRecord ffttwfRecord = ((ITTiAlert3GetLastAlarmFFTCommand) command).getiAlertResponse().get(0);
                            ArrayList<Double> xValues = ffttwfRecord.getTimeDomainDataX();
                            Log.d("Last Alarm FFT/TWF:", String.valueOf(ffttwfRecord.getiAlertTimestamp()));
                        } else if (command instanceof ITTiAlert3GetAlarmFFTsCommand) {
                            ITTiAlert3FFTTWFRecord ffttwfRecord = ((ITTiAlert3GetAlarmFFTsCommand) command).getiAlertResponse().get(0);
                            ArrayList<Double> xValues = ffttwfRecord.getTimeDomainDataX();
                            Log.d("Alarm FFT/TWF received:", String.valueOf(ffttwfRecord.getiAlertTimestamp()));
                        } else if (command instanceof ITTiAlert3GetFluxFFTCommand) {
                            ITTiAlert3FluxFFTTWFRecord fluxFFTRecord = ((ITTiAlert3GetFluxFFTCommand) command).getiAlertResponse();
                            ArrayList<Double> xValues = fluxFFTRecord.getFluxTWFData();
                            Log.d("Flux FFT/TWF received:", String.valueOf(fluxFFTRecord.getiAlertTimestamp()));
                        }

                    } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.PressureSensor.ordinal()) {
                        if (command instanceof ITTPressureGetTrendsCommand) {
                            List<ITTPressureTrendRecord> trendRecords = ((ITTPressureGetTrendsCommand) command).getSensorResponse();
                            Log.d("Pressure Trends: ", String.valueOf(trendRecords.size()));
                        } else if (command instanceof ITTPressureTagReadCommand) {
                            String tag = ((ITTPressureTagReadCommand) command).getTag();
                            Log.d("Pressure Tag read: ", tag);
                        } else if (command instanceof ITTPressureTagWriteCommand) {
                            String tag = ((ITTPressureTagWriteCommand) command).getTag();
                            Log.d("Pressure Tag set: ", tag);
                        } else if (command instanceof ITTPressureGetThresholdsCommand) {
                            ITTPressureThresholds pressureThresholds = ((ITTPressureGetThresholdsCommand) command).getPressureThresholds();
                            Log.d("Threshold Alarm High: ", String.valueOf(pressureThresholds.getAlarmHighPressureThreshold().getPressure()));
                            Log.d("Threshold Alarm High: ", String.valueOf(pressureThresholds.getAlarmHighPressureThreshold().getTemperature()));
                            Log.d("Threshold Warning High ", String.valueOf(pressureThresholds.getWarningHighPressureThreshold().getPressure()));
                            Log.d("Threshold Warning High ", String.valueOf(pressureThresholds.getWarningHighPressureThreshold().getTemperature()));
                            Log.d("Threshold Warning Low: ", String.valueOf(pressureThresholds.getWarningLowPressureThreshold().getPressure()));
                            Log.d("Threshold Warning Low: ", String.valueOf(pressureThresholds.getWarningLowPressureThreshold().getTemperature()));
                            Log.d("Threshold Alarm Low: ", String.valueOf(pressureThresholds.getAlarmLowPressureThreshold().getPressure()));
                            Log.d("Threshold Alarm Low: ", String.valueOf(pressureThresholds.getAlarmLowPressureThreshold().getTemperature()));

                        } else if (command instanceof ITTPressureGetTypeCommand) {
                            ITTPressureSensorTypeRecord pressureSensorTypeRecord = ((ITTPressureGetTypeCommand) command).getiAlertResponse();
                            Log.d("Pressure Accuracy: ", String.valueOf(pressureSensorTypeRecord.getAccuracy()));
                            Log.d("Pressure Connection: ", String.valueOf(pressureSensorTypeRecord.getConnection()));
                            Log.d("Pressure Range: ", String.valueOf(pressureSensorTypeRecord.getRange()));
                            Log.d("Pressure Series: ", String.valueOf(pressureSensorTypeRecord.getSeries()));

                        } else if (command instanceof ITTPressureEraseCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Erase command: ", String.valueOf(success));
                        } else if (command instanceof ITTPressureSetThresholdAlarmHighCommand) {
                            ITTPressureMutableThreshold threshold = ((ITTPressureSetThresholdAlarmHighCommand) command).getThreshold();
                            Log.d("Threshold Alarm High: ", String.valueOf(threshold.getPressure()));
                            Log.d("Threshold Alarm High: ", String.valueOf(threshold.getTemperature()));

                        } else if (command instanceof ITTPressureSetThresholdWarningHighCommand) {
                            ITTPressureMutableThreshold threshold = ((ITTPressureSetThresholdWarningHighCommand) command).getThreshold();
                            Log.d("Threshold Warning High ", String.valueOf(threshold.getPressure()));
                            Log.d("Threshold Warning High ", String.valueOf(threshold.getTemperature()));

                        } else if (command instanceof ITTPressureGetAcquisitionRateCommand) {
                            ITTPressureAcquisitionRateRecord rateRecord = ((ITTPressureGetAcquisitionRateCommand) command).getiAlertResponse();
                            Log.d("acquisitionRate read: ", String.valueOf(rateRecord.getAcquisitionRateInMinutes()));
                        } else if (command instanceof ITTPressureSetAcquisitionRateCommand) {
                            int acquisitionRateInMinutes = ((ITTPressureSetAcquisitionRateCommand) command).getAcquisitionRateInMinutes();
                            Log.d("acquisitionRate set:", String.valueOf(acquisitionRateInMinutes));
                        } else if (command instanceof ITTPressureZeroPressureCommand) {
                            boolean success = (Boolean) iAlertResponse;
                            Log.d("Zero pressure command: ", String.valueOf(success));
                        }
                    }


                } else if (error != null) {   // in this case iAlertResponse is null
                    Log.d("Error: ", error.toString());
                    if (compoundCommand != null) {
                        List<ITTBLECommand> finishedCommands = compoundCommand.getiAlertResponse();
                        Log.d("Commands completed: ", String.valueOf(finishedCommands.size()));
                    }
                }
            }
        };


        commandProgressHandler = new ITTBLECommand.ITTBLECommandProgress() {
            @Override
            public void onProgressUpdate(int progress) {
                if (compoundCommand != null) {
                    Log.d("Compound command: ", String.valueOf(progress));
                    List<ITTBLECommand> finishedCommands = compoundCommand.getiAlertResponse();
                    Log.d("Commands completed: ", String.valueOf(finishedCommands.size()));
                    return;
                }

                // The calls below are invoked only in the case of a single command. In the case of a compound command, the
                // handling of the progress update of each individual command is done by the SDK always inside the compound command itself.
                if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert2.ordinal()) {
                    if (command instanceof ITTiAlertGetHourlyAlarmTrendsCommand) {
                        Log.d("Alarm Trends Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTiAlertGetHourlyTrendsCommand) {
                        Log.d("Trends Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTGetAlarmFFTsCommand) {
                        Log.d("Alarm FFTs Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTGetAlarmTWFsCommand) {
                        Log.d("Alarm TWFs Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTGetLiveReadingCommand) {
                        Log.d("Live Reading Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTGetLongFFTCommand) {
                        Log.d("Long FFT Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTGetShortFFTCommand) {
                        Log.d("Short FFT Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTGetWeeklyTrendsCommand) {
                        Log.d("Weekly Trends Progress:", String.valueOf(progress));
                    } else if (command instanceof ITTGetAlarmFFTsAndTWFsCommand) {
                        Log.d("Alarm FFTTWF Progress:", String.valueOf(progress));
                    }

                } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert3.ordinal()) {
                    if (command instanceof ITTiAlert3GetTrendsCommand) {
                        Log.d("Trends Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTiAlert3GetManualFFTCommand) {
                        Log.d("FFT Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTiAlert3GetLastAlarmFFTCommand) {
                        Log.d("FFT Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTiAlert3GetAlarmFFTsCommand) {
                        Log.d("Alarm FFT Progress: ", String.valueOf(progress));
                    } else if (command instanceof ITTiAlert3GetFluxFFTCommand) {
                        Log.d("Flux FFT Progress: ", String.valueOf(progress));
                    }
                } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.PressureSensor.ordinal()) {
                    if (command instanceof ITTPressureGetTrendsCommand) {
                        Log.d("Trends Progress: ", String.valueOf(progress));
                    }
                }
            }
        };



        if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert2.ordinal()) {
            items.add("ITTTagReadCommand");
            items.add("ITTiAlertGetTimestampsCommand");
            items.add("ITTiAlertGetHourlyTrendsCommand");
            items.add("ITTTagWriteCommand");
            items.add("ITTCountersReadCommand");
            items.add("ITTGetAlarmFFTsCommand");
            items.add("ITTGetAlarmTWFsCommand");
            items.add("ITTGetDeviceStatsCommand");
            items.add("ITTGetLiveReadingCommand");
            items.add("ITTGetLongFFTCommand");
            items.add("ITTGetRunningThresholdCommand");
            items.add("ITTGetShortFFTCommand");
            items.add("ITTGetThresholdsHighCommand");
            items.add("ITTGetThresholdsLowCommand");
            items.add("ITTGetWeeklyTrendsCommand");
            items.add("ITTiAlertBaselineRequestCommand");
            items.add("ITTiAlertEraseCommand");
            items.add("ITTiAlertGetHourlyAlarmTrendsCommand");
            items.add("ITTiAlertResetRuntimeCommand");
            items.add("ITTiAlertSleepCommand");
            items.add("ITTRequestNewLongFFTCommand");
            items.add("ITTRequestNewShortFFTCommand");
            items.add("ITTSetRunningThresholdCommand");
            items.add("ITTSetThresholdsHighCommand");
            items.add("ITTSetThresholdsLowCommand");
            items.add("ITTSystemParamsReadCommand");
            items.add("ITTAcquisitionRateWriteCommand");
            items.add("ITTAlarmWakeUpIntervalWriteCommand");
            items.add("ITTGetAlarmFFTsAndTWFsCommand");
        } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert3.ordinal()) {
            items.add("ITTiAlert3SystemStructureReadCommand");
            items.add("ITTiAlert3SystemStructureWriteCommand");
            items.add("ITTiAlert3GetTrendsCommand");
            items.add("ITTiAlert3GetManualFFTCommand");
            items.add("ITTiAlert3GetLastAlarmFFTsCommand");
            items.add("ITTiAlert3GetAlarmFFTsCommand");
            items.add("ITTiAlert3GetFluxFFTCommand");
            items.add("ITTiAlert3SystemStructureWriteCommand");
            items.add("ITTiAlert3SystemStructureWriteCommand");
            items.add("ITTiAlert3SystemStructureWriteCommand");
            items.add("ITTiAlert3SystemStructureWriteCommand");
            //TODO: Add all commands
        } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.PressureSensor.ordinal()) {
            items.add("ITTPressureGetThresholdsCommand");
            items.add("ITTPressureGetTrendsCommand");
            items.add("ITTPressureGetTypeCommand");
            items.add("ITTPressureGetAcquisitionRateCommand");
            items.add("ITTPressureSetAcquisitionRateCommand");
            items.add("ITTPressureEraseCommand");
            items.add("ITTPressureSetThresholdAlarmHighCommand");
            items.add("ITTPressureSetThresholdWarningHighCommand");
            items.add("ITTPressureTagReadCommand");
            items.add("ITTPressureTagWriteCommand");
            items.add("ITTPressureZeroPressureCommand");
        }

        setUpCommandsMap();

        mAdapter = new CommandArrayAdapter(this, items);
        list = (ListView) findViewById(R.id.list_commands);
        list.setClickable(true);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selections.contains(position)) {
                    selections.add(position);
                } else {
                    selections.remove((Integer)position);
                }
                mAdapter.notifyDataSetChanged();
                updateMenuItem();
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (device != null) {
            sensorManager.closeCurrentConnection(false);
        }
    }



    private void setUpCommandsMap() {
        for (int position = 0; position < items.size(); position++) {
            initiateCommand(position);
            commandsMap.put(position, command);
        }
    }





    private void updateMenuItem() {
        if (selections.size() == 0) {
            executeItem.setVisible(false);
        } else if (selections.size() == 1) {
            executeItem.setTitle(R.string.execute_name);
            executeItem.setVisible(true);
        } else {
            executeItem.setTitle(R.string.execute_many_name);
            executeItem.setVisible(true);
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_command, menu);
        executeItem = menu.findItem(R.id.action_execute);

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                resetAll();
                finish();
                return true;

            case R.id.action_execute:
                commandsMap.clear();
                setUpCommandsMap();
                Integer[] indices = new Integer[selections.size()];
                if (selections.size() == 1) {
                    compoundCommand = null;
                    int position = selections.toArray(indices)[0];
                    initiateCommand(position);
                    sensorManager.executeCommand(command, device, packet);
                } else {
                    initiateCompoundCommand(selections.toArray(indices));
                    sensorManager.executeCommand(compoundCommand, device, packet);
                }
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    private void resetAll() {
        items.clear();
        selections.clear();
        commandsMap.clear();
    }





    private void initiateCommand(int position) {
        if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert2.ordinal()) {
            switch (position) {
                case 0:
                    command = new ITTTagReadCommand();
                    break;
                case 1:
                    command = new ITTiAlertGetTimestampsCommand(ITTiAlertGetTimestampsCommand.ITTiAlertBulkDataType.AlarmFFTs);
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 2:
                    command = new ITTiAlertGetHourlyTrendsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 3:
                    command = new ITTTagWriteCommand("NEW TAG");
                    break;
                case 4:
                    command = new ITTCountersReadCommand();
                    break;
                case 5:
                    command = new ITTGetAlarmFFTsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 6:
                    command = new ITTGetAlarmTWFsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 7:
                    command = new ITTGetDeviceStatsCommand();
                    break;
                case 8:
                    command = new ITTGetLiveReadingCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 9:
                    command = new ITTGetLongFFTCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 10:
                    command = new ITTGetRunningThresholdCommand();
                    break;
                case 11:
                    command = new ITTGetShortFFTCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 12:
                    command = new ITTGetThresholdsHighCommand();
                    break;
                case 13:
                    command = new ITTGetThresholdsLowCommand();
                    break;
                case 14:
                    command = new ITTGetWeeklyTrendsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 15:
                    command = new ITTiAlertBaselineRequestCommand();
                    break;
                case 16:
                    command = new ITTiAlertEraseCommand();
                    break;
                case 17:
                    command = new ITTiAlertGetHourlyAlarmTrendsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 18:
                    command = new ITTiAlertResetRuntimeCommand();
                    break;
                case 19:
                    command = new ITTiAlertSleepCommand();
                    break;
                case 20:
                    command = new ITTRequestNewLongFFTCommand();
                    break;
                case 21:
                    command = new ITTRequestNewShortFFTCommand();
                    break;
                case 22:
                    command = new ITTSetRunningThresholdCommand(0.6f);
                    break;
                case 23:
                    ITTiAlertMutableThreshold thresholdHigh = ITTiAlertMutableThreshold.recordWithVibrationsAndTemperature(0.8, 0.9, 1.2, 68, (ITTAdvertisingPacketInterface) packet);
                    command = new ITTSetThresholdsHighCommand(thresholdHigh);
                    break;
                case 24:
                    ITTiAlertMutableThreshold thresholdLow = ITTiAlertMutableThreshold.recordWithVibrationsAndTemperature(0.4, 0.5, 0.6, 38, (ITTAdvertisingPacketInterface) packet);
                    command = new ITTSetThresholdsLowCommand(thresholdLow);
                    break;
                case 25:
                    command = new ITTSystemParamsReadCommand();
                    break;
                case 26:
                    int acquisitionRateInMinutes = 30;
                    command = new ITTAcquisitionRateWriteCommand(acquisitionRateInMinutes);
                    break;
                case 27:
                    int alarmWakeUpIntervalInMinutes = 4;
                    command = new ITTAlarmWakeUpIntervalWriteCommand(alarmWakeUpIntervalInMinutes);
                    break;
                case 28:
                    command = new ITTGetAlarmFFTsAndTWFsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                default:
                    break;
            }

        } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert3.ordinal()) {
            switch (position) {
                case 0:
                    command = new ITTiAlert3SystemStructureReadCommand();
                    break;
                case 1:
                    ITTiAlert3MutableSystemStructure mutableSystemStructure = ITTiAlert3MutableSystemStructure.recordWithPacket((ITTiAlert3AdvertisingPacketInterface) packet);
                    ITTAxialValues vibeVelocityAlarmThresholds = new ITTAxialValues(1.9, 1.8, 1.7);
                    ITTAxialValues vibeVelocityWarningThresholds = new ITTAxialValues(0.4, 0.4, 0.4);
                    mutableSystemStructure.setThresholds(vibeVelocityAlarmThresholds, vibeVelocityWarningThresholds, 85.0, 75.0);
                    mutableSystemStructure.setTemperatureOffsetInCelsius(0.0);
                    mutableSystemStructure.setBandConfiguration(250, 500, 0.13, 3000, 3500, 0.25);
                    mutableSystemStructure.setIntervalsInSeconds(60*60, 5*60);
                    mutableSystemStructure.setTag("SQ-MDE");
                    mutableSystemStructure.setDeviceSettings(1, 1, 3, 2, false, true, true);
                    mutableSystemStructure.setDoAlarmCheck(false);
                    mutableSystemStructure.setDoAutoTrend(false);
                    mutableSystemStructure.setDoManualTrend(false);
                    //mutableSystemStructure.setDoManualVibrationFFTTWF(true);
                    mutableSystemStructure.setDoAlarmVibrationFFTTWF(false);
                    //mutableSystemStructure.setDoFluxFFT(true);
                    //mutableSystemStructure.setDoRunSpeed(true);
                    mutableSystemStructure.setStartNewBaseline(false);
                    mutableSystemStructure.setDoFactoryReset(false);
                    //mutableSystemStructure.setEnableSmartTrend(false);
                    mutableSystemStructure.setResetRunAndAlarmTime(false);
                    mutableSystemStructure.setClearAllAlarmsAndAlarmFFTCounter(false);
                    mutableSystemStructure.setRequestSystemReset(false);
                    mutableSystemStructure.setSetHighResolutionRunSpeedCalculation(true);
                    //mutableSystemStructure.setEnableWiredMode(false);
                    mutableSystemStructure.setClearOverTempFlag(false);
                    mutableSystemStructure.setStartBootloader(false);
                    mutableSystemStructure.setRunningThreshold(310.0);
                    mutableSystemStructure.setRunMinInG(0.2);
                    command = new ITTiAlert3SystemStructureWriteCommand(mutableSystemStructure);
                    break;
                case 2:
                    command = new ITTiAlert3GetTrendsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 3:
                    command = new ITTiAlert3GetManualFFTCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 4:
                    command = new ITTiAlert3GetLastAlarmFFTCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 5:
                    command = new ITTiAlert3GetAlarmFFTsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 6:
                    command = new ITTiAlert3GetFluxFFTCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 7:
                    ITTiAlert3MutableSystemStructure requestNewTrendSystemStructure = ITTiAlert3MutableSystemStructure.recordWithPacket((ITTiAlert3AdvertisingPacketInterface) packet); //Note: This can be combined with any other function within System Structure
                    requestNewTrendSystemStructure.setDoManualTrend(true);
                    command = new ITTiAlert3SystemStructureWriteCommand(requestNewTrendSystemStructure);
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 8:
                    ITTiAlert3MutableSystemStructure requestNewVibrationFFTTWFSystemStructure = ITTiAlert3MutableSystemStructure.recordWithPacket((ITTiAlert3AdvertisingPacketInterface) packet);  //Note: This can be combined with any other function within System Structure
                    requestNewVibrationFFTTWFSystemStructure.setDoManualVibrationFFTTWF(true);
                    command = new ITTiAlert3SystemStructureWriteCommand(requestNewVibrationFFTTWFSystemStructure);
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 9:
                    ITTiAlert3MutableSystemStructure requestNewFluxFFTTWFSystemStructure = ITTiAlert3MutableSystemStructure.recordWithPacket((ITTiAlert3AdvertisingPacketInterface) packet);  //Note: This can be combined with any other function within System Structure
                    requestNewFluxFFTTWFSystemStructure.setDoFluxFFT(true);
                    command = new ITTiAlert3SystemStructureWriteCommand(requestNewFluxFFTTWFSystemStructure);
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 10:
                    ITTiAlert3MutableSystemStructure comibnationMutableSystemStructure = ITTiAlert3MutableSystemStructure.recordWithPacket((ITTiAlert3AdvertisingPacketInterface) packet);
                    ITTAxialValues combinationVibeVelocityAlarmThresholds = new ITTAxialValues(0.5, 0.6, 0.7);
                    ITTAxialValues combinationVibeVelocityWarningThresholds = new ITTAxialValues(0.4, 0.5, null);
                    comibnationMutableSystemStructure.setThresholds(combinationVibeVelocityAlarmThresholds, combinationVibeVelocityWarningThresholds, 30.5, 25.3);
                    comibnationMutableSystemStructure.setTemperatureOffsetInCelsius(0.2);
                    comibnationMutableSystemStructure.setBandConfiguration(50, 500, 0.2, 800, 1200, 0.3);
                    comibnationMutableSystemStructure.setIntervalsInSeconds(58*60, 4*60);
                    comibnationMutableSystemStructure.setTag("TEST iA3#1");

                    comibnationMutableSystemStructure.setEnableSmartTrend(false);
                    comibnationMutableSystemStructure.setSetHighResolutionRunSpeedCalculation(true);

                    comibnationMutableSystemStructure.setDoManualTrend(true);
                    comibnationMutableSystemStructure.setDoManualVibrationFFTTWF(true);
                    comibnationMutableSystemStructure.setDoFluxFFT(true);
                    comibnationMutableSystemStructure.setDoRunSpeed(true);

                    command = new ITTiAlert3SystemStructureWriteCommand(comibnationMutableSystemStructure);
                default:
                    break;
                //TODO: Add all commands
            }
            if (command != null) command.setCustomMtuSize(23);

        } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.PressureSensor.ordinal()) {
            switch (position) {
                case 0:
                    command = new ITTPressureGetThresholdsCommand();
                    break;
                case 1:
                    command = new ITTPressureGetTrendsCommand();
                    command.setCommandProgressHandler(commandProgressHandler);
                    break;
                case 2:
                    command = new ITTPressureGetTypeCommand();
                    break;
                case 3:
                    command = new ITTPressureGetAcquisitionRateCommand();
                    break;
                case 4:
                    int acquisitionRateInMinutes = 40;
                    command = new ITTPressureSetAcquisitionRateCommand(acquisitionRateInMinutes);
                    break;
                case 5:
                    command = new ITTPressureEraseCommand();
                    break;
                case 6:
                    ITTPressureMutableThreshold alarmHighMutableThreshold = ITTPressureMutableThreshold.recordWithPressureAndTemperature(98, 96, (ITTPressureSensorAdvertisingPacketInterface) packet);
                    command = new ITTPressureSetThresholdAlarmHighCommand(alarmHighMutableThreshold);
                    break;
                case 7:
                    ITTPressureMutableThreshold warningHighMutableThreshold = ITTPressureMutableThreshold.recordWithPressureAndTemperature(89, 81, (ITTPressureSensorAdvertisingPacketInterface) packet);
                    command = new ITTPressureSetThresholdWarningHighCommand(warningHighMutableThreshold);
                    break;
                case 8:
                    command = new ITTPressureTagReadCommand();
                    break;
                case 9:
                    command = new ITTPressureTagWriteCommand("PRESS ANDR 2");
                    break;
                case 10:
                    command = new ITTPressureZeroPressureCommand();
                    break;
                default:
                    break;
            }
        }

        command.setCommandStatusHandler(commandStatusHandler);
        command.setCommandCompletionHandler(commandCompletionHandler);
        //sensorManager.executeCommand(command, device);
    }







    private void initiateCompoundCommand(Integer[] indices) {
        List<ITTBLECommand> commands = new ArrayList<>();
        for (int i = 0; i < indices.length; i++) {
            commands.add(commandsMap.get(indices[i]));
        }
        compoundCommand = ITTBLECompoundCommand.compoundCommandWithCommands(commands);
        compoundCommand.setCommandStatusHandler(commandStatusHandler);
        compoundCommand.setCommandCompletionHandler(commandCompletionHandler);
        compoundCommand.setCommandProgressHandler(commandProgressHandler);
        //sensorManager.executeCommand(compoundCommand, device);
    }









    public class CommandArrayAdapter extends ArrayAdapter {
        public List<String> items;
        private Context context;
        private LayoutInflater inflater;


        public CommandArrayAdapter(Context context, List<String> items) {
            super(context, R.layout.command_list_item, items);
            this.items = items;
            this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }




        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = inflater.inflate(R.layout.command_list_item, parent, false);
            }

            if (selections.contains(position)) {
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.color_selected));
            } else {
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.color_not_selected));
            }

            final View help = v;
            TextView tag = (TextView) v.findViewById(R.id.sensorCommand);
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    ((AbsListView)list).performItemClick(help, position, position);
                }
            });



            if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert2.ordinal()) {
                if (position == 0) {
                    tag.setText("Read the tag of the sensor");
                } else if (position == 1) {
                    tag.setText("Get the timestamps of the Alarm FFTs");
                } else if (position == 2) {
                    tag.setText("Get the Trends of the sensor");
                } else if (position == 3) {
                    tag.setText("Set the tag: NEW TAG to the sensor");
                } else if (position == 4) {
                    tag.setText("Get the Counters of the sensor");
                } else if (position == 5) {
                    tag.setText("Get the Alarm FFTs of the sensor");
                } else if (position == 6) {
                    tag.setText("Get the Alarm TWFs of the sensor");
                } else if (position == 7) {
                    tag.setText("Get the Device Stats of the sensor");
                } else if (position == 8) {
                    tag.setText("Get a live reading from the sensor");
                } else if (position == 9) {
                    tag.setText("Get the Long FFT stored in the sensor");
                } else if (position == 10) {
                    tag.setText("Get the Running Threshold of the sensor");
                } else if (position == 11) {
                    tag.setText("Get the Short FFT stored in the sensor");
                } else if (position == 12) {
                    tag.setText("Get the High Threshold of the sensor");
                } else if (position == 13) {
                    tag.setText("Get the Low Threshold of the sensor");
                } else if (position == 14) {
                    tag.setText("Get the Weekly Trends of the sensor");
                } else if (position == 15) {
                    tag.setText("Make a Baseline Request to the sensor");
                } else if (position == 16) {
                    tag.setText("Erase the data stored in the sensor");
                } else if (position == 17) {
                    tag.setText("Get the Alarm Hourly Trends of the sensor");
                } else if (position == 18) {
                    tag.setText("Reset the runtime of the sensor");
                } else if (position == 19) {
                    tag.setText("Make a Sleep request to the sensor");
                } else if (position == 20) {
                    tag.setText("Request a new Long FFT from the sensor");
                } else if (position == 21) {
                    tag.setText("Request a new Short FFT from the sensor");
                } else if (position == 22) {
                    tag.setText("Set the Running Threshold of the sensor to 0.6");
                } else if (position == 23) {
                    tag.setText("Set the High Threshold of the sensor");
                } else if (position == 24) {
                    tag.setText("Set the Low Threshold of the sensor");
                } else if (position == 25) {
                    tag.setText("Get the acquisition and alarmWakeUp rates of the sensor");
                } else if (position == 26) {
                    tag.setText("Set the acquisition rate of the sensor");
                } else if (position == 27) {
                    tag.setText("Set the alarmWakeUp rate of the sensor");
                } else if (position == 28) {
                    tag.setText("Get the Alarm FFTs and TWFs of the sensor");
                }

            } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.iAlert3.ordinal()) {
                if (position == 0) {
                    tag.setText("Read the System Structure of the sensor");
                } else if (position == 1) {
                    tag.setText("Write the System Structure of the sensor");
                } else if (position == 2) {
                    tag.setText("Get the Trends of the sensor");
                } else if (position == 3) {
                    tag.setText("Get the Manual FFT stored in the sensor");
                } else if (position == 4) {
                    tag.setText("Get the Last Alarm FFT stored in the sensor");
                } else if (position == 5) {
                    tag.setText("Get the Alarm FFTs stored in the sensor");
                } else if (position == 6) {
                    tag.setText("Get the Flux FFT stored in the sensor");
                } else if (position == 7) {
                    tag.setText("Request for a Manual Trend");
                } else if (position == 8) {
                    tag.setText("Request for a Manual Vibration FFT/TWF");
                } else if (position == 9) {
                    tag.setText("Request for a Manual Flux FFT/TWF");
                } else if (position == 10) {
                    tag.setText("Combine Multiple Config and Measurements");
                }
            } else if (packet.getPeripheralType().ordinal() == ITTLEPacket.SensorType.PressureSensor.ordinal()) {
                if (position == 0) {
                    tag.setText("Get all the Thresholds of the sensor");
                } else if (position == 1) {
                    tag.setText("Get the Trends of the sensor");
                } else if (position == 2) {
                    tag.setText("Get the Type information of the sensor");
                } else if (position == 3) {
                    tag.setText("Get the acquisition rate of the sensor");
                } else if (position == 4) {
                    tag.setText("Set the acquisition rate of the sensor");
                } else if (position == 5) {
                    tag.setText("Erase the data stored in the sensor");
                } else if (position == 6) {
                    tag.setText("Set the Alarm High Threshold of the sensor");
                } else if (position == 7) {
                    tag.setText("Set the Warning High Threshold of the sensor");
                } else if (position == 8) {
                    tag.setText("Read the tag of the sensor");
                } else if (position == 9) {
                    tag.setText("Set the tag: PRESS ANDR 2 to the sensor");
                } else if (position == 10) {
                    tag.setText("Set the current pressure level as the zero point for the sensor");
                }
            }

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

    }




}
