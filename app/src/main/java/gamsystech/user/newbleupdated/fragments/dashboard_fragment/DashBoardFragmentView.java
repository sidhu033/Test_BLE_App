package gamsystech.user.newbleupdated.fragments.dashboard_fragment;

import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.model.TreatmentResponseModel;

public interface DashBoardFragmentView
{
    interface View
    {

        void OnFailed(String error);
        void Onsucess(TreatmentRequestModel treatmentRequestModel);
    }

    interface Action
    {
        void OnBpTreatmentComplete(TreatmentRequestModel treatmentRequestModel);

        void launchReadingDemo(TreatmentResponseModel treatmentResponseModel, TreatmentRequestModel treatmentRequestModel);
    }

}

