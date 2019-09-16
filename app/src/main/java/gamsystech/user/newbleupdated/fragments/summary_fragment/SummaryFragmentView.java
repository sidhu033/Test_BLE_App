package gamsystech.user.newbleupdated.fragments.summary_fragment;

import gamsystech.user.newbleupdated.model.TreatmentRequestModel;

public interface SummaryFragmentView
{
    interface  View
    {
        void OnFailed(String error);
    }

    interface  Action
    {
        void OnBPCountTreament(TreatmentRequestModel treatmentRequestModel);
    }
}
