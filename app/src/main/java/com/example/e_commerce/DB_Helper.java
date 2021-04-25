package com.example.e_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DB_Helper extends SQLiteOpenHelper {
    private static String DB_Name = "E_Commerce";
    SQLiteDatabase database;
    public DB_Helper(@Nullable Context context) {
        super(context, DB_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table CustomersTable(CustID integer primary key autoincrement," +
                "CustName text not null,Username text not null," +
                "password text not null,Gender text not null," +
                "Birthdate text not null,job text not null)");

        db.execSQL("create table CategoriesTable(CatID integer primary key autoincrement," +
                " CatName text not null)");

        db.execSQL("create table ProductsTable(ProdID integer primary key autoincrement," +
                "ProdName text not null,Price real not null,Quantity integer not null," +
                "CatID integer not null,FOREIGN KEY(CatID) REFERENCES CategoriesTable(CatID))");

        db.execSQL("create table Orders(OrdID integer primary key autoincrement," +
                "OrdDate text not null,CustID integer not null,Address text not null," +
                "FOREIGN KEY(CustID) REFERENCES CustomersTable(CustID))");

        db.execSQL("create table OrderDetailsTable(OrdID integer not null ," +
                "ProdID integer not null " +
                ",Quantity integer default 1," +
                "FOREIGN KEY(OrdID) REFERENCES OrdersTable(OrdID)," +
                "FOREIGN KEY(ProdID) REFERENCES ProductsTable(ProdID)," +
                "PRIMARY KEY(OrdID,ProdID))");

        initializTable_Categories(db);
        initializTable_Products(db);
        initializTable_Customers(db);
        initializeTable_Orders(db);
        initializTable_OrderDetails(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists CustomersTable");
        db.execSQL("drop table if exists OrdersTable");
        db.execSQL("drop table if exists CategoriesTable");
        db.execSQL("drop table if exists ProductsTable");
        db.execSQL("drop table if exists OrderDetailsTable");
        onCreate(db);
    }

    private void initializTable_Customers(SQLiteDatabase db)
    {
        ContentValues row=new ContentValues();
        row.put("CustName","Alaa");
        row.put("Username","alaama7moud34@gmail.com");
        row.put("Password","Alaa01117960698");
        row.put("Gender","Female");
        row.put("Birthdate","21/1/1999");
        row.put("job","Student");
        db.insert("CustomersTable",null,row);
        //db.close();
    }

    private void initializeTable_Orders(SQLiteDatabase db)
    {
        ContentValues row=new ContentValues();
        row.put("OrdDate","21/9/2020");
        row.put("CustID",1);
        row.put("Address","Shoubra elkhiema");
        db.insert("OrdersTable",null,row);

        row=new ContentValues();
        row.put("OrdDate","21/10/2020");
        row.put("CustID",1);
        row.put("Address","Shoubra elkhiema");
        db.insert("OrdersTable",null,row);

        //db.close();
    }

    private void initializTable_Categories(SQLiteDatabase db)
    {
        ContentValues row=new ContentValues();
        row.put("CatName","Bags");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Shoes");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Supermarket");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Sports");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Kitchen Basics");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Toys");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Men Clothes");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Women Clothes");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Electronics");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Health & Beauty");
        db.insert("CategoriesTable",null,row);

        row=new ContentValues();
        row.put("CatName","Home Furniture");
        db.insert("CategoriesTable",null,row);

        //db.close();
    }

    private void initializTable_Products(SQLiteDatabase db)
    {
        ContentValues row=new ContentValues();
        row.put("ProdName","Black Cross Bag");
        row.put("Price","135");
        row.put("Quantity","5");
        row.put("CatID","1");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Havan half boot");
        row.put("Price","245");
        row.put("Quantity","8");
        row.put("CatID","2");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","samsung smart TV 43-inch");
        row.put("Price","4000");
        row.put("Quantity","10");
        row.put("CatID","9");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Labtop bed tray table");
        row.put("Price","140");
        row.put("Quantity","30");
        row.put("CatID","11");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Ferrero nutella & go spread/sticks");
        row.put("Price","16");
        row.put("Quantity","100");
        row.put("CatID","3");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","TwixTop chocolate bars,21g x 20");
        row.put("Price","77");
        row.put("Quantity","100");
        row.put("CatID","3");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Cadbury hot cocoa powder 3 in 1- 10 sachets,30g");
        row.put("Price","37.95");
        row.put("Quantity","100");
        row.put("CatID","3");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Holw el-sham ice cream with chocolate,75gm");
        row.put("Price","6.5");
        row.put("Quantity","100");
        row.put("CatID","3");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","L'Oreal paris pure green face mask");
        row.put("Price","120");
        row.put("Quantity","20");
        row.put("CatID","10");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Vasline petroleum jelly original,100ml");
        row.put("Price","33");
        row.put("Quantity","25");
        row.put("CatID","10");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","1 Million by paco rabanne for men-Eau de Toilette,50ml");
        row.put("Price","942.5");
        row.put("Quantity","40");
        row.put("CatID","10");
        db.insert("ProductsTable",null,row);

        row=new ContentValues();
        row.put("ProdName","Sauvage by Dior for men-Eau de Toilette,100ml");
        row.put("Price","1425");
        row.put("Quantity","35");
        row.put("CatID","10");
        db.insert("ProductsTable",null,row);

        //db.close();
    }

    private void initializTable_OrderDetails(SQLiteDatabase db)
    {
        ContentValues row=new ContentValues();
        row.put("OrdID","1");
        row.put("ProdID","1");
        row.put("Quantity","2");
        db.insert("OrderDetailsTable",null,row);

        row=new ContentValues();
        row.put("OrdID","2");
        row.put("ProdID","2");
        row.put("Quantity","1");
        db.insert("OrderDetailsTable",null,row);
        //db.close();
    }

    public void add_customer(String name,String Email,String Password,String Gender,String Birthdate,String job)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("CustName",name);
        row.put("Username",Email);
        row.put("Password",Password);
        row.put("Gender",Gender);
        row.put("Birthdate",Birthdate);
        row.put("job",job);
        database.insert("CustomersTable",null,row);
        database.close();
    }

    public boolean IsValidLogin(String username, String Password)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select Username,password From CustomersTable where Username ='"+username+"' and password='"+Password+"'",null);
        if(cursor!=null && cursor.getCount()!=0) {
            database.close();
            return true;
        }
        database.close();
        return false;
    }

    public boolean IsCustomer(String username)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select Username From CustomersTable where Username ='"+username+"'",null);
        if(cursor!=null && cursor.getCount()!=0) {
            database.close();
            return true;
        }
        database.close();
        return false;
    }

    public Cursor AllCategories()
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select CatName From CategoriesTable",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }
    public Cursor get_orders(int Custid)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * From Orders where CustID='"+Custid+"'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public Cursor retProducts(String catName)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from ProductsTable p inner join CategoriesTable c on p.CatID=c.CatID where c.CatName='"+catName+"'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public Cursor search(String str)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from ProductsTable where ProdName LIKE'%"+str+"%'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public Cursor product_Details(String name)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from ProductsTable where ProdName LIKE'%"+name+"%'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public int getCustomerID(String username)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select CustID from CustomersTable where Username='"+username+"'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor.getInt(0);
    }

    public Cursor getCustomerInfo(String username)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from CustomersTable where Username='"+username+"'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Add_Order(String date, int Cust_id, String address, String ProdName, int Quantity) throws UnsupportedEncodingException {
        database=this.getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("OrdDate",date);
        row.put("CustID",Cust_id);
        row.put("Address",new String(address.getBytes(),UTF_8));
        database.insert("Orders",null,row);
        //database.close();

        database=this.getReadableDatabase();
        Cursor Order_cursor = database.rawQuery("Select OrdID from Orders",null);
        Order_cursor.moveToLast();
        //database.close();

        database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select ProdID from ProductsTable where ProdName ='"+ProdName+"'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        //database.close();

        database=this.getWritableDatabase();
        row=new ContentValues();
        row.put("OrdID",Order_cursor.getInt(0));
        row.put("ProdID",cursor.getInt(0));
        row.put("Quantity",Quantity);
        database.insert("OrderDetailsTable",null,row);
        database.close();
    }
}
