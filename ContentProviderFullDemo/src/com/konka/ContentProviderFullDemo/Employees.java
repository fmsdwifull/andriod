package com.konka.ContentProviderFullDemo;

import android.net.Uri;
import android.provider.BaseColumns;


public class Employees {

    public static final String AUTHORITY = "com.konka.provider.Employees";
    
    private Employees() {}
    
    // �ڲ���
    public static final class Employee implements BaseColumns {
        
    	// ���췽��
        private Employee() {}
        
        // ����Uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/employee");
        
        // ��������
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.amaker.employees";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.amaker.employees";
        
        // Ĭ��������
        public static final String DEFAULT_SORT_ORDER = "name DESC";// ����������
        
        // ���ֶγ���
        public static final String NAME = "name";                    // ����
        public static final String GENDER= "gender";                // �Ա�
        public static final String AGE = "age";                     // ����
    }		
}