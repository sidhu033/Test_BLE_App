package gamsystech.user.newbleupdated.activities.readingdemo_activity;

import java.util.List;

public interface ReadingDemoView {

    interface View
    {
        void OnFailed(String error);

        void onSuccess(List<TreatmentGetResponseModel.TreatmentDetail> treatmentDetails);
    }

    interface Action
    {
       void getTreatmentData(TreatmentGetRequestModel treatmentGetRequestModel);
    }
}
