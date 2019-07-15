package sketch.settings;

import java.util.ArrayList;
import java.util.HashMap;

public class HMIDropDownData {
    private static HMIDropDownData instance = new HMIDropDownData();

    private DisplayDropDownData displayDropDownData = null;
    private ActiveDropDownData activeDropDownData = null;
    private ActionDropDownData actionDropDownData = null;
    private HKDropDownData hkDropDownData = null;
    private InitDropDownData initDropDownData = null;
    private StatusChangeDropDownData statusChangeDropDownData = null;
    private TransitionDropDownData transitionDropDownData = null;
    private TrigDropDownData trigDropDownData = null;

    public static HMIDropDownData getInstance() {
        return instance;
    }

    public HMIDropDownData() {

    }

    public DisplayDropDownData getDisplayDropDownDataInstance() {
        if (displayDropDownData == null) {
            displayDropDownData = new DisplayDropDownData();
        }
        return displayDropDownData;
    }

    public ActiveDropDownData getActiveDropDownDataInstance() {
        if (activeDropDownData == null) {
            activeDropDownData = new ActiveDropDownData();
        }
        return activeDropDownData;
    }

    public ActionDropDownData getActionDropDownDataInstance() {
        if (actionDropDownData == null) {
            actionDropDownData = new ActionDropDownData();
        }
        return actionDropDownData;
    }

    public HKDropDownData getHkDropDownDataInstance() {
        if (hkDropDownData == null) {
            hkDropDownData = new HKDropDownData();
        }
        return hkDropDownData;
    }

    public InitDropDownData getInitDropDownDataInstance() {
        if (initDropDownData == null) {
            initDropDownData = new InitDropDownData();
        }
        return initDropDownData;
    }

    public StatusChangeDropDownData getStatusChangeDropDownDataInstance() {
        if (statusChangeDropDownData == null) {
            statusChangeDropDownData = new StatusChangeDropDownData();
        }
        return statusChangeDropDownData;
    }

    public TransitionDropDownData getTransitionDropDownDataInstance() {
        if (transitionDropDownData == null) {
            transitionDropDownData = new TransitionDropDownData();
        }
        return transitionDropDownData;
    }

    public TrigDropDownData getTrigDropDownDataInstance() {
        if (trigDropDownData == null) {
            trigDropDownData = new TrigDropDownData();
        }
        return trigDropDownData;
    }

    public class DisplayDropDownData {
        private ArrayList<String> displayInSuchConditionData = new ArrayList<>();
        private ArrayList<String> addDisplayConditionInViewModelData = new ArrayList<>();
        private ArrayList<String> displayConditionData = new ArrayList<>();
        private ArrayList<String> displayConditionInViewModel = new ArrayList<>();
        private ArrayList<String> propertyTypeData = new ArrayList<>();
        private ArrayList<String> propertyData = new ArrayList<>();

        public void setDisplayInSucnConditionData(ArrayList<String> displayInSuchConditionData) {
            this.displayInSuchConditionData = displayInSuchConditionData;
        }

        public String[] getDisplayInSuchConditionData() {
            String[] displayInSuchConditionDataArr = new String[displayInSuchConditionData.size()];
            for (int i = 0; i < displayInSuchConditionData.size(); ++i) {
                displayInSuchConditionDataArr[i] = displayInSuchConditionData.get(i);
            }
            return displayInSuchConditionDataArr;
        }

        public void setAddDisplayConditionInViewModelData(ArrayList<String> addDisplayConditionInViewModelData) {
            this.addDisplayConditionInViewModelData = addDisplayConditionInViewModelData;
        }

        public String[] getAddDisplayConditionInViewModelData() {
            String[] addDisplayConditionInViewModelDataArr = new String[addDisplayConditionInViewModelData.size()];
            for (int i = 0; i < addDisplayConditionInViewModelData.size(); ++i) {
                addDisplayConditionInViewModelDataArr[i] = addDisplayConditionInViewModelData.get(i);
            }
            return addDisplayConditionInViewModelDataArr;
        }

        public String getAddDisplayConditionInViewModelFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "Z" + row;
            String table_array = "{";
            String[] displayInSuchConditionDataArr = getDisplayInSuchConditionData();
            String[] addDisplayConditionInViewModelDataArr = getAddDisplayConditionInViewModelData();
            for (int i = 0; i < displayInSuchConditionDataArr.length; ++i) {
                table_array += "\"" + displayInSuchConditionDataArr[i]+ "\"" + "," + "\"" + addDisplayConditionInViewModelDataArr[i] + "\"";
                if (i + 1 == displayInSuchConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }


        public void setDisplayConditionData(ArrayList<String> displayConditionData) {
            this.displayConditionData = displayConditionData;
        }

        public String[] getDisplayConditionData() {
            String[] displayConditionDataArr = new String[displayConditionData.size()];
            for (int i = 0; i < displayConditionData.size(); ++i) {
                displayConditionDataArr[i] = displayConditionData.get(i);
            }
            return displayConditionDataArr;
        }

        public void setDisplayConditionInViewModel(ArrayList<String> displayConditionInViewModel) {
            this.displayConditionInViewModel = displayConditionInViewModel;
        }

        public String[] getDisplayConditionInViewModel() {
            String[] displayConditionInViewModelArr = new String[displayConditionInViewModel.size()];
            for (int i = 0; i < displayConditionInViewModel.size(); ++i) {
                displayConditionInViewModelArr[i] = displayConditionInViewModel.get(i);
            }
            return displayConditionInViewModelArr;
        }

        public String getDisplayConditionInViewModelFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "T" + row;
            String table_array = "{";
            String[] displayConditionDataArr = getDisplayConditionData();
            String[] displayConditionInViewModelDataArr = getDisplayConditionInViewModel();
            for (int i = 0; i < displayConditionDataArr.length; ++i) {
                table_array += "\"" + displayConditionDataArr[i]+ "\"" + "," + "\"" + displayConditionInViewModelDataArr[i] + "\"";
                if (i + 1 == displayConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setPropertyTypeData(ArrayList<String> propertyTypeData) {
            this.propertyTypeData = propertyTypeData;
        }

        public String[] getPropertyTypeData() {
            String[] propertyTypeDataArr = new String[propertyTypeData.size()];
            for (int i = 0; i < propertyTypeData.size(); ++i) {
                propertyTypeDataArr[i] = propertyTypeData.get(i);
            }
            return propertyTypeDataArr;
        }

        public void setPropertyData(ArrayList<String> propertyData) {
            this.propertyData = propertyData;
        }

        public String[] getPropertyData() {
            String[] propertyDataArr = new String[propertyData.size()];
            for (int i = 0; i < propertyData.size(); ++i) {
                propertyDataArr[i] = propertyData.get(i);
            }
            return propertyDataArr;
        }

        public String getPropertyDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "AF" + row;
            String table_array = "{";
            String[] propertyTypeDataArr = getPropertyTypeData();
            String[] propertyDataArr = getPropertyData();
            for (int i = 0; i < propertyTypeDataArr.length; ++i) {
                table_array += "\"" + propertyTypeDataArr[i]+ "\"" + "," + "\"" + propertyDataArr[i] + "\"";
                if (i + 1 == propertyTypeDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }
    }

    public class ActiveDropDownData {
        private ArrayList<String> displayInSuchConditionData = new ArrayList<>();
        private ArrayList<String> addDisplayConditionInViewModelData = new ArrayList<>();
        private ArrayList<String> conditionData = new ArrayList<>();
        private ArrayList<String> displayConditionInViewModelData = new ArrayList<>();
        private ArrayList<String> propertyTypeData = new ArrayList<>();
        private ArrayList<String> propertyData = new ArrayList<>();
        private ArrayList<String> duringDriving = new ArrayList<>();

        public void setDisplayInSucnConditionData(ArrayList<String> displayInSuchConditionData) {
            this.displayInSuchConditionData = displayInSuchConditionData;
        }

        public String[] getDisplayInSuchConditionData() {
            String[] displayInSuchConditionDataArr = new String[displayInSuchConditionData.size()];
            for (int i = 0; i < displayInSuchConditionData.size(); ++i) {
                displayInSuchConditionDataArr[i] = displayInSuchConditionData.get(i);
            }
            return displayInSuchConditionDataArr;
        }

        public void setAddDisplayConditionInViewModelData(ArrayList<String> addDisplayConditionInViewModelData) {
            this.addDisplayConditionInViewModelData = addDisplayConditionInViewModelData;
        }

        public String[] getAddDisplayConditionInViewModelData() {
            String[] addDisplayConditionInViewModelDataArr = new String[addDisplayConditionInViewModelData.size()];
            for (int i = 0; i < addDisplayConditionInViewModelData.size(); ++i) {
                addDisplayConditionInViewModelDataArr[i] = addDisplayConditionInViewModelData.get(i);
            }
            return addDisplayConditionInViewModelDataArr;
        }

        public String getAddDisplayConditionInViewModelFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "X" + row;
            String table_array = "{";
            String[] displayInSuchConditionDataArr = getDisplayInSuchConditionData();
            String[] addDisplayConditionInViewModelDataArr = getAddDisplayConditionInViewModelData();
            for (int i = 0; i < displayInSuchConditionDataArr.length; ++i) {
                table_array += "\"" + displayInSuchConditionDataArr[i]+ "\"" + "," + "\"" + addDisplayConditionInViewModelDataArr[i] + "\"";
                if (i + 1 == displayInSuchConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setConditionData(ArrayList<String> conditionData) {
            this.conditionData = conditionData;
        }

        public String[] getConditionData() {
            String[] conditionDataArr = new String[conditionData.size()];
            for (int i = 0; i < conditionData.size(); ++i) {
                conditionDataArr[i] = conditionData.get(i);
            }
            return conditionDataArr;
        }

        public void setDisplayConditionInViewModelData(ArrayList<String> displayConditionInViewModelData) {
            this.displayConditionInViewModelData = displayConditionInViewModelData;
        }

        public String[] getDisplayConditionInViewModelData() {
            String[] displayConditionInViewModelDataArr = new String[displayConditionInViewModelData.size()];
            for (int i = 0; i < displayConditionInViewModelData.size(); ++i) {
                displayConditionInViewModelDataArr[i] = displayConditionInViewModelData.get(i);
            }
            return displayConditionInViewModelDataArr;
        }

        public String getDisplayConditionInViewModelFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "R" + row;
            String table_array = "{";
            String[] displayConditionDataArr = getConditionData();
            String[] displayConditionInViewModelDataArr = getDisplayConditionInViewModelData();
            for (int i = 0; i < displayConditionDataArr.length; ++i) {
                table_array += "\"" + displayConditionDataArr[i]+ "\"" + "," + "\"" + displayConditionInViewModelDataArr[i] + "\"";
                if (i + 1 == displayConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setPropertyTypeData(ArrayList<String> propertyTypeData) {
            this.propertyTypeData = propertyTypeData;
        }

        public String[] getPropertyTypeData() {
            String[] propertyTypeDataArr = new String[propertyTypeData.size()];
            for (int i = 0; i < propertyTypeData.size(); ++i) {
                propertyTypeDataArr[i] = propertyTypeData.get(i);
            }
            return propertyTypeDataArr;
        }

        public void setPropertyData(ArrayList<String> propertyData) {
            this.propertyData = propertyData;
        }

        public String[] getPropertyData() {
            String[] propertyDataArr = new String[propertyData.size()];
            for (int i = 0; i < propertyData.size(); ++i) {
                propertyDataArr[i] = propertyData.get(i);
            }
            return propertyDataArr;
        }

        public String getPropertyDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "AF" + row;
            String table_array = "{";
            String[] propertyTypeDataArr = getPropertyTypeData();
            String[] propertyDataArr = getPropertyData();
            for (int i = 0; i < propertyTypeDataArr.length; ++i) {
                table_array += "\"" + propertyTypeDataArr[i]+ "\"" + "," + "\"" + propertyDataArr[i] + "\"";
                if (i + 1 == propertyTypeDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setDuringDriving(ArrayList<String> duringDriving) {
            this.duringDriving = duringDriving;
        }

        public String[] getDuringDriving() {
            String[] duringDrivingArr = new String[duringDriving.size()];
            for (int i = 0; i < duringDriving.size(); ++i) {
                duringDrivingArr[i] = duringDriving.get(i);
            }
            return duringDrivingArr;
        }
    }

    public class ActionDropDownData {
        private ArrayList<String> conditionOfActionData = new ArrayList<>();
        private ArrayList<String> viewModelData = new ArrayList<>();
        private ArrayList<String> actionInSuchConditionData = new ArrayList<>();
        private ArrayList<String> funcOfModelData = new ArrayList<>();
        private ArrayList<String> opeTypeData = new ArrayList<>();
        private ArrayList<String> eventData = new ArrayList<>();
        private ArrayList<String> soundData = new ArrayList<>();

        public void setConditionOfActionData(ArrayList<String> conditionOfActionData) {
            this.conditionOfActionData = conditionOfActionData;
        }

        public String[] getConditionOfActionData() {
            String[] conditionOfActionDataArr = new String[conditionOfActionData.size()];
            for (int i = 0; i < conditionOfActionData.size(); ++i) {
                conditionOfActionDataArr[i] = conditionOfActionData.get(i);
            }
            return conditionOfActionDataArr;
        }

        public void setViewModelData(ArrayList<String> viewModelData) {
            this.viewModelData = viewModelData;
        }

        public String[] getViewModelData() {
            String[] viewModelDataArr = new String[viewModelData.size()];
            for (int i = 0; i < viewModelData.size(); ++i) {
                viewModelDataArr[i] = viewModelData.get(i);
            }
            return viewModelDataArr;
        }

        public String getViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "R" + row;
            String table_array = "{";
            String[] conditionOfActionDataArr = getConditionOfActionData();
            String[] viewModelDataArr = getViewModelData();
            for (int i = 0; i < conditionOfActionDataArr.length; ++i) {
                table_array += "\"" + conditionOfActionDataArr[i]+ "\"" + "," + "\"" + viewModelDataArr[i] + "\"";
                if (i + 1 == conditionOfActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setActionInSuchConditionData(ArrayList<String> actionInSuchConditionData) {
            this.actionInSuchConditionData = actionInSuchConditionData;
        }

        public String[] getActionInSuchConditionData() {
            String[] actionInSuchConditionDataArr = new String[actionInSuchConditionData.size()];
            for (int i = 0; i < actionInSuchConditionData.size(); ++i) {
                actionInSuchConditionDataArr[i] = actionInSuchConditionData.get(i);
            }
            return actionInSuchConditionDataArr;
        }

        public void setFuncOfModelData(ArrayList<String> funcOfModelData) {
            this.funcOfModelData = funcOfModelData;
        }

        public String[] getFuncOfModelData() {
            String[] funcOfModelDataArr = new String[funcOfModelData.size()];
            for (int i = 0; i < funcOfModelData.size(); ++i) {
                funcOfModelDataArr[i] = funcOfModelData.get(i);
            }
            return funcOfModelDataArr;
        }

        public String getFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "X" + row;
            String table_array = "{";
            String[] actionInSuchConditionDataArr = getActionInSuchConditionData();
            String[] funcOfModelDataArr = getFuncOfModelData();
            for (int i = 0; i < actionInSuchConditionDataArr.length; ++i) {
                table_array += "\"" + actionInSuchConditionDataArr[i]+ "\"" + "," + "\"" + funcOfModelDataArr[i] + "\"";
                if (i + 1 == actionInSuchConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setOpeTypeData(ArrayList<String> opeTypeData) {
            this.opeTypeData = opeTypeData;
        }

        public String[] getOpeTypeData() {
            String[] opeTypeDataArr = new String[opeTypeData.size()];
            for (int i = 0; i < opeTypeData.size(); ++i) {
                opeTypeDataArr[i] = opeTypeData.get(i);
            }
            return opeTypeDataArr;
        }

        public void setEventData(ArrayList<String> eventData) {
            this.eventData = eventData;
        }

        public String[] getEventData() {
            String[] eventDataArr = new String[eventData.size()];
            for (int i = 0; i < eventData.size(); ++i) {
                eventDataArr[i] = eventData.get(i);
            }
            return eventDataArr;
        }

        public String getEventDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "L" + row;
            String table_array = "{";
            String[] opeTypeDataArr = getOpeTypeData();
            String[] eventDataArr = getEventData();
            for (int i = 0; i < opeTypeDataArr.length; ++i) {
                table_array += "\"" + opeTypeDataArr[i]+ "\"" + "," + "\"" + eventDataArr[i] + "\"";
                if (i + 1 == opeTypeDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setSoundData(ArrayList<String> soundData) {
            this.soundData = soundData;
        }

        public String[] getSoundData() {
            String[] soundDataArr = new String[soundData.size()];
            for (int i = 0; i < soundData.size(); ++i) {
                soundDataArr[i] = soundData.get(i);
            }
            return soundDataArr;
        }
    }

    public class HKDropDownData {
        private ArrayList<String> conditionOfActionData = new ArrayList<>();
        private ArrayList<String> viewModelData = new ArrayList<>();
        private ArrayList<String> actionInSuchConditionData = new ArrayList<>();
        private ArrayList<String> funcOfModelData = new ArrayList<>();
        private ArrayList<String> opeTypeData = new ArrayList<>();
        private ArrayList<String> eventData = new ArrayList<>();
        private ArrayList<String> soundData = new ArrayList<>();
        private ArrayList<String> duringDriving = new ArrayList<>();

        public void setConditionOfActionData(ArrayList<String> conditionOfActionData) {
            this.conditionOfActionData = conditionOfActionData;
        }

        public String[] getConditionOfActionData() {
            String[] conditionOfActionDataArr = new String[conditionOfActionData.size()];
            for (int i = 0; i < conditionOfActionData.size(); ++i) {
                conditionOfActionDataArr[i] = conditionOfActionData.get(i);
            }
            return conditionOfActionDataArr;
        }

        public void setViewModelData(ArrayList<String> viewModelData) {
            this.viewModelData = viewModelData;
        }

        public String[] getViewModelData() {
            String[] viewModelDataArr = new String[viewModelData.size()];
            for (int i = 0; i < viewModelData.size(); ++i) {
                viewModelDataArr[i] = viewModelData.get(i);
            }
            return viewModelDataArr;
        }

        public String getViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "R" + row;
            String table_array = "{";
            String[] conditionOfActionDataArr = getConditionOfActionData();
            String[] viewModelDataArr = getViewModelData();
            for (int i = 0; i < conditionOfActionDataArr.length; ++i) {
                table_array += "\"" + conditionOfActionDataArr[i]+ "\"" + "," + "\"" + viewModelDataArr[i] + "\"";
                if (i + 1 == conditionOfActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setActionInSuchConditionData(ArrayList<String> actionInSuchConditionData) {
            this.actionInSuchConditionData = actionInSuchConditionData;
        }

        public String[] getActionInSuchConditionData() {
            String[] actionInSuchConditionDataArr = new String[actionInSuchConditionData.size()];
            for (int i = 0; i < actionInSuchConditionData.size(); ++i) {
                actionInSuchConditionDataArr[i] = actionInSuchConditionData.get(i);
            }
            return actionInSuchConditionDataArr;
        }

        public void setFuncOfModelData(ArrayList<String> funcOfModelData) {
            this.funcOfModelData = funcOfModelData;
        }

        public String[] getFuncOfModelData() {
            String[] funcOfModelDataArr = new String[funcOfModelData.size()];
            for (int i = 0; i < funcOfModelData.size(); ++i) {
                funcOfModelDataArr[i] = funcOfModelData.get(i);
            }
            return funcOfModelDataArr;
        }

        public String getFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "X" + row;
            String table_array = "{";
            String[] actionInSuchConditionDataArr = getActionInSuchConditionData();
            String[] funcOfModelDataArr = getFuncOfModelData();
            for (int i = 0; i < actionInSuchConditionDataArr.length; ++i) {
                table_array += "\"" + actionInSuchConditionDataArr[i]+ "\"" + "," + "\"" + funcOfModelDataArr[i] + "\"";
                if (i + 1 == actionInSuchConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setOpeTypeData(ArrayList<String> opeTypeData) {
            this.opeTypeData = opeTypeData;
        }

        public String[] getOpeTypeData() {
            String[] opeTypeDataArr = new String[opeTypeData.size()];
            for (int i = 0; i < opeTypeData.size(); ++i) {
                opeTypeDataArr[i] = opeTypeData.get(i);
            }
            return opeTypeDataArr;
        }

        public void setEventData(ArrayList<String> eventData) {
            this.eventData = eventData;
        }

        public String[] getEventData() {
            String[] eventDataArr = new String[eventData.size()];
            for (int i = 0; i < eventData.size(); ++i) {
                eventDataArr[i] = eventData.get(i);
            }
            return eventDataArr;
        }

        public String getEventDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "L" + row;
            String table_array = "{";
            String[] opeTypeDataArr = getOpeTypeData();
            String[] eventDataArr = getEventData();
            for (int i = 0; i < opeTypeDataArr.length; ++i) {
                table_array += "\"" + opeTypeDataArr[i]+ "\"" + "," + "\"" + eventDataArr[i] + "\"";
                if (i + 1 == opeTypeDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setSoundData(ArrayList<String> soundData) {
            this.soundData = soundData;
        }

        public String[] getSoundData() {
            String[] soundDataArr = new String[soundData.size()];
            for (int i = 0; i < soundData.size(); ++i) {
                soundDataArr[i] = soundData.get(i);
            }
            return soundDataArr;
        }

        public void setDuringDriving(ArrayList<String> duringDriving) {
            this.duringDriving = duringDriving;
        }

        public String[] getDuringDriving() {
            String[] duringDrivingArr = new String[duringDriving.size()];
            for (int i = 0; i < duringDriving.size(); ++i) {
                duringDrivingArr[i] = duringDriving.get(i);
            }
            return duringDrivingArr;
        }
    }

    public class InitDropDownData {
        private ArrayList<String> conditionOfActionData = new ArrayList<>();
        private ArrayList<String> viewModelData = new ArrayList<>();
        private ArrayList<String> actionInSuchConditionData = new ArrayList<>();
        private ArrayList<String> funcOfModelData = new ArrayList<>();

        public void setConditionOfActionData(ArrayList<String> conditionOfActionData) {
            this.conditionOfActionData = conditionOfActionData;
        }

        public String[] getConditionOfActionData() {
            String[] conditionOfActionDataArr = new String[conditionOfActionData.size()];
            for (int i = 0; i < conditionOfActionData.size(); ++i) {
                conditionOfActionDataArr[i] = conditionOfActionData.get(i);
            }
            return conditionOfActionDataArr;
        }

        public void setViewModelData(ArrayList<String> viewModelData) {
            this.viewModelData = viewModelData;
        }

        public String[] getViewModelData() {
            String[] viewModelDataArr = new String[viewModelData.size()];
            for (int i = 0; i < viewModelData.size(); ++i) {
                viewModelDataArr[i] = viewModelData.get(i);
            }
            return viewModelDataArr;
        }

        public String getViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "O" + row;
            String table_array = "{";
            String[] conditionOfActionDataArr = getConditionOfActionData();
            String[] viewModelDataArr = getViewModelData();
            for (int i = 0; i < conditionOfActionDataArr.length; ++i) {
                table_array += "\"" + conditionOfActionDataArr[i]+ "\"" + "," + "\"" + viewModelDataArr[i] + "\"";
                if (i + 1 == conditionOfActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }


        public void setActionInSuchConditionData(ArrayList<String> actionInSuchConditionData) {
            this.actionInSuchConditionData = actionInSuchConditionData;
        }

        public String[] getActionInSuchConditionData() {
            String[] actionInSuchConditionDataArr = new String[actionInSuchConditionData.size()];
            for (int i = 0; i < actionInSuchConditionData.size(); ++i) {
                actionInSuchConditionDataArr[i] = actionInSuchConditionData.get(i);
            }
            return actionInSuchConditionDataArr;
        }

        public void setFuncOfModelData(ArrayList<String> funcOfModelData) {
            this.funcOfModelData = funcOfModelData;
        }

        public String[] getFuncOfModelData() {
            String[] funcOfModelDataArr = new String[funcOfModelData.size()];
            for (int i = 0; i < funcOfModelData.size(); ++i) {
                funcOfModelDataArr[i] = funcOfModelData.get(i);
            }
            return funcOfModelDataArr;
        }

        public String getFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "X" + row;
            String table_array = "{";
            String[] actionInSuchConditionDataArr = getActionInSuchConditionData();
            String[] funcOfModelDataArr = getFuncOfModelData();
            for (int i = 0; i < actionInSuchConditionDataArr.length; ++i) {
                table_array += "\"" + actionInSuchConditionDataArr[i]+ "\"" + "," + "\"" + funcOfModelDataArr[i] + "\"";
                if (i + 1 == actionInSuchConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }
    }

    public class StatusChangeDropDownData {
        private ArrayList<String> fConditionData = new ArrayList<>();
        private ArrayList<String> bConditionData = new ArrayList<>();
        private ArrayList<String> iConditionData = new ArrayList<>();
        private ArrayList<String> fViewModelData = new ArrayList<>();
        private ArrayList<String> bViewModelData = new ArrayList<>();
        private ArrayList<String> iViewModelData = new ArrayList<>();
        private ArrayList<String> fActionData = new ArrayList<>();
        private ArrayList<String> bActionData = new ArrayList<>();
        private ArrayList<String> iActionData = new ArrayList<>();
        private ArrayList<String> fFuncOfModelData = new ArrayList<>();
        private ArrayList<String> bFuncOfModelData = new ArrayList<>();
        private ArrayList<String> iFuncOfModelData = new ArrayList<>();

        public void setfConditionData(ArrayList<String> fConditionData) {
            this.fConditionData = fConditionData;
        }

        public String[] getfConditionData() {
            String[] fConditionDataArr = new String[fConditionData.size()];
            for (int i = 0; i < fConditionData.size(); ++i) {
                fConditionDataArr[i] = fConditionData.get(i);
            }
            return fConditionDataArr;
        }

        public void setbConditionData(ArrayList<String> bConditionData) {
            this.bConditionData = bConditionData;
        }

        public String[] getbConditionData() {
            String[] bConditionDataArr = new String[bConditionData.size()];
            for (int i = 0; i < bConditionData.size(); ++i) {
                bConditionDataArr[i] = bConditionData.get(i);
            }
            return bConditionDataArr;
        }

        public void setiConditionData(ArrayList<String> iConditionData) {
            this.iConditionData = iConditionData;
        }

        public String[] getiConditionData() {
            String[] iConditionDataArr = new String[iConditionData.size()];
            for (int i = 0; i < iConditionData.size(); ++i) {
                iConditionDataArr[i] = iConditionData.get(i);
            }
            return iConditionDataArr;
        }

        public void setfViewModelData(ArrayList<String> fViewModelData) {
            this.fViewModelData = fViewModelData;
        }

        public String[] getfViewModelData() {
            String[] fViewModelDataArr = new String[fViewModelData.size()];
            for (int i = 0; i < fViewModelData.size(); ++i) {
                fViewModelDataArr[i] = fViewModelData.get(i);
            }
            return fViewModelDataArr;
        }

        public void setbViewModelData(ArrayList<String> bViewModelData) {
            this.bViewModelData = bViewModelData;
        }

        public String[] getbViewModelData() {
            String[] bViewModelDataArr = new String[bViewModelData.size()];
            for (int i = 0; i < bViewModelData.size(); ++i) {
                bViewModelDataArr[i] = bViewModelData.get(i);
            }
            return bViewModelDataArr;
        }

        public void setiViewModelData(ArrayList<String> iViewModelData) {
            this.iViewModelData = iViewModelData;
        }

        public String[] getiViewModelData() {
            String[] iViewModelDataArr = new String[iViewModelData.size()];
            for (int i = 0; i < iViewModelData.size(); ++i) {
                iViewModelDataArr[i] = iViewModelData.get(i);
            }
            return iViewModelDataArr;
        }

        public String getfViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "J" + row;
            String table_array = "{";
            String[] fConditionDataArr = getfConditionData();
            String[] fViewModelDataArr = getfViewModelData();
            for (int i = 0; i < fConditionDataArr.length; ++i) {
                table_array += "\"" + fConditionDataArr[i]+ "\"" + "," + "\"" + fViewModelDataArr[i] + "\"";
                if (i + 1 == fConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getbViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "V" + row;
            String table_array = "{";
            String[] bConditionDataArr = getbConditionData();
            String[] bViewModelDataArr = getbViewModelData();
            for (int i = 0; i < bConditionDataArr.length; ++i) {
                table_array += "\"" + bConditionDataArr[i]+ "\"" + "," + "\"" + bViewModelDataArr[i] + "\"";
                if (i + 1 == bConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getiViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "AH" + row;
            String table_array = "{";
            String[] iConditionDataArr = getiConditionData();
            String[] iViewModelDataArr = getiViewModelData();
            for (int i = 0; i < iConditionDataArr.length; ++i) {
                table_array += "\"" + iConditionDataArr[i]+ "\"" + "," + "\"" + iViewModelDataArr[i] + "\"";
                if (i + 1 == iConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setfActionData(ArrayList<String> fActionData) {
            this.fActionData = fActionData;
        }

        public String[] getfActionData() {
            String[] fActionDataArr = new String[fActionData.size()];
            for (int i = 0; i < fActionData.size(); ++i) {
                fActionDataArr[i] = fActionData.get(i);
            }
            return fActionDataArr;
        }

        public void setbActionData(ArrayList<String> bActionData) {
            this.bActionData = bActionData;
        }

        public String[] getbActionData() {
            String[] bActionDataArr = new String[bActionData.size()];
            for (int i = 0; i < bActionData.size(); ++i) {
                bActionDataArr[i] = bActionData.get(i);
            }
            return bActionDataArr;
        }

        public void setiActionData(ArrayList<String> iActionData) {
            this.iActionData = iActionData;
        }

        public String[] getiActionData() {
            String[] iActionDataArr = new String[iActionData.size()];
            for (int i = 0; i < iActionData.size(); ++i) {
                iActionDataArr[i] = iActionData.get(i);
            }
            return iActionDataArr;
        }

        public void setfFuncOfModelData(ArrayList<String> fFuncOfModelData) {
            this.fFuncOfModelData = fFuncOfModelData;
        }

        public String[] getfFuncOfModelData() {
            String[] fFuncOfModelDataArr = new String[fFuncOfModelData.size()];
            for (int i = 0; i < fFuncOfModelData.size(); ++i) {
                fFuncOfModelDataArr[i] = fFuncOfModelData.get(i);
            }
            return fFuncOfModelDataArr;
        }

        public void setbFuncOfModelData(ArrayList<String> bFuncOfModelData) {
            this.bFuncOfModelData = bFuncOfModelData;
        }

        public String[] getbFuncOfModelData() {
            String[] bFuncOfModelDataArr = new String[bFuncOfModelData.size()];
            for (int i = 0; i < bFuncOfModelData.size(); ++i) {
                bFuncOfModelDataArr[i] = bFuncOfModelData.get(i);
            }
            return bFuncOfModelDataArr;
        }

        public void setiFuncOfModelData(ArrayList<String> iFuncOfModelData) {
            this.iFuncOfModelData = iFuncOfModelData;
        }

        public String[] getiFuncOfModelData() {
            String[] iFuncOfModelDataArr = new String[iFuncOfModelData.size()];
            for (int i = 0; i < iFuncOfModelData.size(); ++i) {
                iFuncOfModelDataArr[i] = iFuncOfModelData.get(i);
            }
            return iFuncOfModelDataArr;
        }

        public String getfFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "N" + row;
            String table_array = "{";
            String[] fActionDataArr = getfActionData();
            String[] fFuncOfModelDataArr = getfFuncOfModelData();
            for (int i = 0; i < fActionDataArr.length; ++i) {
                table_array += "\"" + fActionDataArr[i]+ "\"" + "," + "\"" + fFuncOfModelDataArr[i] + "\"";
                if (i + 1 == fActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getbFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "Z" + row;
            String table_array = "{";
            String[] bActionDataArr = getbActionData();
            String[] bFuncOfModelDataArr = getbFuncOfModelData();
            for (int i = 0; i < bActionDataArr.length; ++i) {
                table_array += "\"" + bActionDataArr[i]+ "\"" + "," + "\"" + bFuncOfModelDataArr[i] + "\"";
                if (i + 1 == bActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getiFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "AL" + row;
            String table_array = "{";
            String[] iActionDataArr = getiActionData();
            String[] iFuncOfModelDataArr = getiFuncOfModelData();
            for (int i = 0; i < iActionDataArr.length; ++i) {
                table_array += "\"" + iActionDataArr[i]+ "\"" + "," + "\"" + iFuncOfModelDataArr[i] + "\"";
                if (i + 1 == iActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }
    }

    public class TransitionDropDownData {
        private ArrayList<String> bConditionData = new ArrayList<>();
        private ArrayList<String> fConditionData = new ArrayList<>();
        private ArrayList<String> iConditionData = new ArrayList<>();

        private ArrayList<String> bViewModelData = new ArrayList<>();
        private ArrayList<String> fViewModelData = new ArrayList<>();
        private ArrayList<String> iViewModelData = new ArrayList<>();

        private ArrayList<String> bActionData = new ArrayList<>();
        private ArrayList<String> fActionData = new ArrayList<>();
        private ArrayList<String> iActionData = new ArrayList<>();

        private ArrayList<String> bFuncOfModelData = new ArrayList<>();
        private ArrayList<String> fFuncOfModelData = new ArrayList<>();
        private ArrayList<String> iFuncOfModelData = new ArrayList<>();

        public void setbConditionData(ArrayList<String> bConditionData) {
            this.bConditionData = bConditionData;
        }

        public String[] getbConditionData() {
            String[] bConditionDataArr = new String[bConditionData.size()];
            for (int i = 0; i < bConditionData.size(); ++i) {
                bConditionDataArr[i] = bConditionData.get(i);
            }
            return bConditionDataArr;
        }

        public void setfConditionData(ArrayList<String> fConditionData) {
            this.fConditionData = fConditionData;
        }

        public String[] getfConditionData() {
            String[] fConditionDataArr = new String[fConditionData.size()];
            for (int i = 0; i < fConditionData.size(); ++i) {
                fConditionDataArr[i] = fConditionData.get(i);
            }
            return fConditionDataArr;
        }

        public void setiConditionData(ArrayList<String> iConditionData) {
            this.iConditionData = iConditionData;
        }

        public String[] getiConditionData() {
            String[] iConditionDataArr = new String[iConditionData.size()];
            for (int i = 0; i < iConditionData.size(); ++i) {
                iConditionDataArr[i] = iConditionData.get(i);
            }
            return iConditionDataArr;
        }

        public void setbViewModelData(ArrayList<String> bViewModelData) {
            this.bViewModelData = bViewModelData;
        }

        public String[] getbViewModelData() {
            String[] bViewModelDataArr = new String[bViewModelData.size()];
            for (int i = 0; i < bViewModelData.size(); ++i) {
                bViewModelDataArr[i] = bViewModelData.get(i);
            }
            return bViewModelDataArr;
        }

        public void setfViewModelData(ArrayList<String> fViewModelData) {
            this.fViewModelData = fViewModelData;
        }

        public String[] getfViewModelData() {
            String[] fViewModelDataArr = new String[fViewModelData.size()];
            for (int i = 0; i < fViewModelData.size(); ++i) {
                fViewModelDataArr[i] = fViewModelData.get(i);
            }
            return fViewModelDataArr;
        }

        public void setiViewModelData(ArrayList<String> iViewModelData) {
            this.iViewModelData = iViewModelData;
        }

        public String[] getiViewModelData() {
            String[] iViewModelDataArr = new String[iViewModelData.size()];
            for (int i = 0; i < iViewModelData.size(); ++i) {
                iViewModelDataArr[i] = iViewModelData.get(i);
            }
            return iViewModelDataArr;
        }

        public String getbViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "J" + row;
            String table_array = "{";
            String[] bConditionDataArr = getbConditionData();
            String[] bViewModelDataArr = getbViewModelData();
            for (int i = 0; i < bConditionDataArr.length; ++i) {
                table_array += "\"" + bConditionDataArr[i]+ "\"" + "," + "\"" + bViewModelDataArr[i] + "\"";
                if (i + 1 == bConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getfViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "V" + row;
            String table_array = "{";
            String[] fConditionDataArr = getfConditionData();
            String[] fViewModelDataArr = getfViewModelData();
            for (int i = 0; i < fConditionDataArr.length; ++i) {
                table_array += "\"" + fConditionDataArr[i]+ "\"" + "," + "\"" + fViewModelDataArr[i] + "\"";
                if (i + 1 == fConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getiViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "AH" + row;
            String table_array = "{";
            String[] iConditionDataArr = getiConditionData();
            String[] iViewModelDataArr = getiViewModelData();
            for (int i = 0; i < iConditionDataArr.length; ++i) {
                table_array += "\"" + iConditionDataArr[i]+ "\"" + "," + "\"" + iViewModelDataArr[i] + "\"";
                if (i + 1 == iConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setbActionData(ArrayList<String> bActionData) {
            this.bActionData = bActionData;
        }

        public String[] getbActionData() {
            String[] bActionDataArr = new String[bActionData.size()];
            for (int i = 0; i < bActionData.size(); ++i) {
                bActionDataArr[i] = bActionData.get(i);
            }
            return bActionDataArr;
        }

        public void setfActionData(ArrayList<String> fActionData) {
            this.fActionData = fActionData;
        }

        public String[] getfActionData() {
            String[] fActionDataArr = new String[fActionData.size()];
            for (int i = 0; i < fActionData.size(); ++i) {
                fActionDataArr[i] = fActionData.get(i);
            }
            return fActionDataArr;
        }

        public void setiActionData(ArrayList<String> iActionData) {
            this.iActionData = iActionData;
        }

        public String[] getiActionData() {
            String[] iActionDataArr = new String[iActionData.size()];
            for (int i = 0; i < iActionData.size(); ++i) {
                iActionDataArr[i] = iActionData.get(i);
            }
            return iActionDataArr;
        }

        public void setbFuncOfModelData(ArrayList<String> bFuncOfModelData) {
            this.bFuncOfModelData = bFuncOfModelData;
        }

        public String[] getbFuncOfModelData() {
            String[] bFuncOfModelDataArr = new String[bFuncOfModelData.size()];
            for (int i = 0; i < bFuncOfModelData.size(); ++i) {
                bFuncOfModelDataArr[i] = bFuncOfModelData.get(i);
            }
            return bFuncOfModelDataArr;
        }

        public void setfFuncOfModelData(ArrayList<String> fFuncOfModelData) {
            this.fFuncOfModelData = fFuncOfModelData;
        }

        public String[] getfFuncOfModelData() {
            String[] fFuncOfModelDataArr = new String[fFuncOfModelData.size()];
            for (int i = 0; i < fFuncOfModelData.size(); ++i) {
                fFuncOfModelDataArr[i] = fFuncOfModelData.get(i);
            }
            return fFuncOfModelDataArr;
        }

        public void setiFuncOfModelData(ArrayList<String> iFuncOfModelData) {
            this.iFuncOfModelData = iFuncOfModelData;
        }

        public String[] getiFuncOfModelData() {
            String[] iFuncOfModelDataArr = new String[iFuncOfModelData.size()];
            for (int i = 0; i < iFuncOfModelData.size(); ++i) {
                iFuncOfModelDataArr[i] = iFuncOfModelData.get(i);
            }
            return iFuncOfModelDataArr;
        }

        public String getbFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "N" + row;
            String table_array = "{";
            String[] bActionDataArr = getbActionData();
            String[] bFuncOfModelDataArr = getbFuncOfModelData();
            for (int i = 0; i < bActionDataArr.length; ++i) {
                table_array += "\"" + bActionDataArr[i]+ "\"" + "," + "\"" + bFuncOfModelDataArr[i] + "\"";
                if (i + 1 == bActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getfFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "Z" + row;
            String table_array = "{";
            String[] fActionDataArr = getfActionData();
            String[] fFuncOfModelDataArr = getfFuncOfModelData();
            for (int i = 0; i < fActionDataArr.length; ++i) {
                table_array += "\"" + fActionDataArr[i]+ "\"" + "," + "\"" + fFuncOfModelDataArr[i] + "\"";
                if (i + 1 == fActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public String getiFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "AL" + row;
            String table_array = "{";
            String[] iActionDataArr = getiActionData();
            String[] iFuncOfModelDataArr = getiFuncOfModelData();
            for (int i = 0; i < iActionDataArr.length; ++i) {
                table_array += "\"" + iActionDataArr[i]+ "\"" + "," + "\"" + iFuncOfModelDataArr[i] + "\"";
                if (i + 1 == iActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }
    }

    public class TrigDropDownData {
        private ArrayList<String> conditionOfActionData = new ArrayList<>();
        private ArrayList<String> viewModelData = new ArrayList<>();
        private ArrayList<String> actionInSuchConditionData = new ArrayList<>();
        private ArrayList<String> funcOfModelData = new ArrayList<>();
        private ArrayList<String> soundData = new ArrayList<>();
        private ArrayList<String> nameData = new ArrayList<>();
        private ArrayList<String> triggerData = new ArrayList<>();
        private ArrayList<String> observerData = new ArrayList<>();
        private ArrayList<String> replyData = new ArrayList<>();

        public void setConditionOfActionData(ArrayList<String> conditionOfActionData) {
            this.conditionOfActionData = conditionOfActionData;
        }

        public String[] getConditionOfActionData() {
            String[] conditionOfActionDataArr = new String[conditionOfActionData.size()];
            for (int i = 0; i < conditionOfActionData.size(); ++i) {
                conditionOfActionDataArr[i] = conditionOfActionData.get(i);
            }
            return conditionOfActionDataArr;
        }

        public void setViewModelData(ArrayList<String> viewModelData) {
            this.viewModelData = viewModelData;
        }

        public String[] getViewModelData() {
            String[] viewModelDataArr = new String[viewModelData.size()];
            for (int i = 0; i < viewModelData.size(); ++i) {
                viewModelDataArr[i] = viewModelData.get(i);
            }
            return viewModelDataArr;
        }

        public String getViewModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "O" + row;
            String table_array = "{";
            String[] conditionOfActionDataArr = getConditionOfActionData();
            String[] viewModelDataArr = getViewModelData();
            for (int i = 0; i < conditionOfActionDataArr.length; ++i) {
                table_array += "\"" + conditionOfActionDataArr[i]+ "\"" + "," + "\"" + viewModelDataArr[i] + "\"";
                if (i + 1 == conditionOfActionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setActionInSuchConditionData(ArrayList<String> actionInSuchConditionData) {
            this.actionInSuchConditionData = actionInSuchConditionData;
        }

        public String[] getActionInSuchConditionData() {
            String[] actionInSuchConditionDataArr = new String[actionInSuchConditionData.size()];
            for (int i = 0; i < actionInSuchConditionData.size(); ++i) {
                actionInSuchConditionDataArr[i] = actionInSuchConditionData.get(i);
            }
            return actionInSuchConditionDataArr;
        }

        public void setFuncOfModelData(ArrayList<String> funcOfModelData) {
            this.funcOfModelData = funcOfModelData;
        }

        public String[] getFuncOfModelData() {
            String[] funcOfModelDataArr = new String[funcOfModelData.size()];
            for (int i = 0; i < funcOfModelData.size(); ++i) {
                funcOfModelDataArr[i] = funcOfModelData.get(i);
            }
            return funcOfModelDataArr;
        }

        public String getFuncOfModelDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "V" + row;
            String table_array = "{";
            String[] actionInSuchConditionDataArr = getActionInSuchConditionData();
            String[] funcOfModelDataArr = getFuncOfModelData();
            for (int i = 0; i < actionInSuchConditionDataArr.length; ++i) {
                table_array += "\"" + actionInSuchConditionDataArr[i]+ "\"" + "," + "\"" + funcOfModelDataArr[i] + "\"";
                if (i + 1 == actionInSuchConditionDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setSoundData(ArrayList<String> soundData) {
            this.soundData = soundData;
        }

        public String[] getSoundData() {
            String[] soundDataArr = new String[soundData.size()];
            for (int i = 0; i < soundData.size(); ++i) {
                soundDataArr[i] = soundData.get(i);
            }
            return soundDataArr;
        }

        public void setNameData(ArrayList<String> nameData) {
            this.nameData = nameData;
        }

        public String[] getNameData() {
            String[] nameDataArr = new String[nameData.size()];
            for (int i = 0; i < nameData.size(); ++i) {
                nameDataArr[i] = nameData.get(i);
            }
            return nameDataArr;
        }

        public void setTriggerData(ArrayList<String> triggerData) {
            this.triggerData = triggerData;
        }

        public String[] getTriggerData() {
            String[] triggerDataArr = new String[triggerData.size()];
            for (int i = 0; i < triggerData.size(); ++i) {
                triggerDataArr[i] = triggerData.get(i);
            }
            return triggerDataArr;
        }

        public String getTriggerDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "F" + row;
            String table_array = "{";
            String[] nameDataArr = getNameData();
            String[] triggerDataArr = getTriggerData();
            for (int i = 0; i < nameDataArr.length; ++i) {
                table_array += "\"" + nameDataArr[i]+ "\"" + "," + "\"" + triggerDataArr[i] + "\"";
                if (i + 1 == nameDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setObserverData(ArrayList<String> observerData) {
            this.observerData = observerData;
        }

        public String[] getObserverData() {
            String[] observerDataArr = new String[observerData.size()];
            for (int i = 0; i < observerData.size(); ++i) {
                observerDataArr[i] = observerData.get(i);
            }
            return observerDataArr;
        }

        public String getObserverDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "F" + row;
            String table_array = "{";
            String[] nameDataArr = getNameData();
            String[] observerDataArr = getObserverData();
            for (int i = 0; i < nameDataArr.length; ++i) {
                table_array += "\"" + nameDataArr[i]+ "\"" + "," + "\"" + observerDataArr[i] + "\"";
                if (i + 1 == nameDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }

        public void setReplyData(ArrayList<String> replyData) {
            this.replyData = replyData;
        }

        public String[] getReplyData() {
            String[] replyDataArr = new String[replyData.size()];
            for (int i = 0; i < replyData.size(); ++i) {
                replyDataArr[i] = replyData.get(i);
            }
            return replyDataArr;
        }

        public String getReplyDataFormulaString(int row) {
            String formulaString = "";
            String lookup_value = "F" + row;
            String table_array = "{";
            String[] nameDataArr = getNameData();
            String[] replyDataArr = getReplyData();
            for (int i = 0; i < nameDataArr.length; ++i) {
                table_array += "\"" + nameDataArr[i]+ "\"" + "," + "\"" + replyDataArr[i] + "\"";
                if (i + 1 == nameDataArr.length) {
                    table_array += "}";
                } else {
                    table_array += ";";
                }
            }
            String col_index_num = "2";
            String range_lookup = "0";
            formulaString = "VLOOKUP(" + lookup_value + "," + table_array + "," + col_index_num + "," + range_lookup + ")";
            return formulaString;
        }
    }
}
