package com.app.FirstApp.domain.SaveMethodes;

import org.springframework.stereotype.Component;

@Component
public class StatusConstant {

    public static final String Raison01="First Upload";
    public static final String Status01="Rejected";
    public static final String Raison02="Xml Invalid";

    public static final String Status1="Uploaded";
    public static final String Raison1="Contraintes Valides";
    public static final String Status2="Kms_Processing";
    public static final String Raison2="KMS_Received_SF";
    public static final String Status3="Odm_Processing";
    public static final String Raison3="Kms return OK";
    public static final String Status4="Import-Aborded";

    public static final String Raison4="Kms return Ko";
}
