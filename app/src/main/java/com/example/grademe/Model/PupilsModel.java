package com.example.grademe.Model;

import android.arch.lifecycle.MutableLiveData;

import com.example.grademe.domain.Pupil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PupilsModel implements Serializable {

    private MutableLiveData<List<Pupil>> pupils;
    private final Long qrcode;
    public PupilsModel(Long qrcode,List<Pupil> pupils){
        this.qrcode = qrcode;
        this.pupils = new MutableLiveData<List<Pupil>>();
        getPupils();
        this.pupils.setValue(pupils);
    }
    public MutableLiveData<List<Pupil>> getPupils(){
        //TODO IMPLEMENT REST CALL
        //
//        IF NO PUPILS IN MODULE RETURN EMPTY LIST
        if(this.pupils.getValue()== null){
            this.pupils.setValue(new ArrayList<Pupil>());
        }
        return this.pupils;

    }
    public Long getQrcode(){
        return this.qrcode;
    }

}
