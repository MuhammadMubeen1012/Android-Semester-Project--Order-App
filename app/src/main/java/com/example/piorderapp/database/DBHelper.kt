package com.example.piorderapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.piorderapp.models.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null , DB_VERSION) {

    companion object{
        //DB variables
        private val DB_NAME = "pi.db"
        private val DB_VERSION = 3
        //Table variables
        val TABLE_USER = "user"
        val TABLE_USER_ROLE = "user_role"
        val TABLE_COMPANIES_GROUP = "companies_group"
        val TABLE_COMPANY = "company"
        val TABLE_PRODUCT = "product"
        val TABLE_CUSTOMER = "customer"
        val TABLE_CUSTOMER_ORDER = "customer_order"
        val TABLE_ORDER_ONELINE = "order_oneline"
        /*
        //Column variables
        val COLS_USER_TABLE = arrayOf("user_ID" , "user_name" , "user_password", "user_role_ID")
        val COLS_USER_ROLE_TABLE = arrayOf("user_role_ID" , "user_role_name")
        val COLS_GROUP_TABLE = arrayOf("group_ID" , "group_name")
        val COLS_COMPANY_TABLE = arrayOf("company_ID" , "company_name" , "group_ID")
        val COLS_PRODUCT_TABLE = arrayOf("product_ID" , "product_name", "product_packing" , "product_price")
        val COLS_CUSTOMER_TABLE = arrayOf("customer_ID" , "customer_name", "customer_address")
        val COLS_ORDER_TABLE = arrayOf("order_ID" , "order_date", "order_TP_price" , "order_RT_price")
        val COLS_ORDER_ONELINE_TABLE = arrayOf("oneline_ID","oneline_QTY" , "oneline_price")
         */
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_USER (user_id INTEGER PRIMARY KEY AUTOINCREMENT, user_name TEXT UNIQUE, user_password TEXT , user_role_ID INTEGER , FOREIGN KEY(user_role_ID) REFERENCES $TABLE_USER_ROLE(user_role_ID))")
        db?.execSQL("CREATE TABLE $TABLE_USER_ROLE (user_role_ID INTEGER PRIMARY KEY AUTOINCREMENT,user_role_name TEXT UNIQUE)")
        db?.execSQL("CREATE TABLE $TABLE_COMPANIES_GROUP (group_ID INTEGER PRIMARY KEY AUTOINCREMENT, group_name TEXT UNIQUE)")
        db?.execSQL("CREATE TABLE $TABLE_COMPANY (company_ID INTEGER PRIMARY KEY AUTOINCREMENT, company_name TEXT UNIQUE, group_ID INTEGER, FOREIGN KEY(group_ID) REFERENCES $TABLE_COMPANIES_GROUP(group_ID))")
        db?.execSQL("CREATE TABLE $TABLE_PRODUCT (product_ID INTEGER PRIMARY KEY AUTOINCREMENT, product_name TEXT UNIQUE, product_packing TEXT, product_price REAL, company_ID INTEGER , FOREIGN KEY(company_ID) REFERENCES $TABLE_COMPANY(company_ID))")
        db?.execSQL("CREATE TABLE $TABLE_CUSTOMER (customer_ID INTEGER PRIMARY KEY AUTOINCREMENT, customer_name TEXT UNIQUE, customer_address TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_CUSTOMER_ORDER (order_ID INTEGER PRIMARY KEY AUTOINCREMENT, order_date TEXT, order_TP_price REAL, order_salesman TEXT, order_for_customer TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_ORDER_ONELINE (oneline_ID INTEGER PRIMARY KEY AUTOINCREMENT, oneline_product TEXT, oneline_productPCK TEXT , oneline_QTY INTEGER, oneline_price REAL, oneline_TP REAL, order_ID INTEGER, FOREIGN KEY(order_ID) REFERENCES $TABLE_CUSTOMER_ORDER(order_ID))")

        val isAdminAdded: Boolean = this.addAdmin(db!!)
        val isAdminRoleAdded: Boolean = this.addRoles("admin",db!!)
        val isSMRoleAdded: Boolean = this.addRoles("salesman",db!!)
        val isOperatorRoleAdded: Boolean = this.addRoles("operator",db!!)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_USER_ROLE")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMER")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_COMPANIES_GROUP")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_COMPANY")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMER_ORDER")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_ONELINE")
        onCreate(p0)
    }

    //predefined | hardcoded
    fun addAdmin(db: SQLiteDatabase): Boolean {
        //val db = this.writableDatabase
        //we have to take the values
        val values = ContentValues()
        //put values
        values.put("user_name","admin")
        values.put("user_password","main@pi01")
        //insert into table
        val added: Long = db.insert(TABLE_USER,null,values)
        return (Integer.parseInt("$added")!=-1)
    }

    //predefined | hardcoded
    fun addRoles(roleName: String , db: SQLiteDatabase): Boolean{
        //val db = this.writableDatabase
        //we have to take the values
        val values = ContentValues()
        //put values
        values.put("user_role_name",roleName)
        //insert into table
        val added: Long = db.insert(TABLE_USER_ROLE,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    //getting roles
    fun getRole(userName: String , password: String): Int{
        val input_username: String = userName
        val input_password: String = password

        val cursor: Cursor = this.writableDatabase.rawQuery("SELECT * FROM $TABLE_USER",null)

        if(cursor!=null){
            //Log.d("Cursor Testing: " , "Cursor is not null")
            if(cursor.moveToFirst()){
                do{
                    val username: String = cursor.getString(1)
                    val password: String = cursor.getString(2)
                    val user_role_ID: Int = cursor.getInt(3)
                    val id: Int = cursor.getInt(0)
                    Log.d("Data" , "${id} , ${username} , ${password} , ${user_role_ID}")
                    if(username.equals(input_username) && password.equals(input_password)){
                        //Log.d("CP-1" , "UR-ID True")
                        return user_role_ID
                    }
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return -1
    }

    // ----- User Table operation ----- //
    fun getUsers(): List<PIUserModel>{
        val userList = ArrayList<PIUserModel>()
        //query
        val selectUser_query = "SELECT * FROM $TABLE_USER"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectUser_query,null)
//        db.close()
        if(cursor!=null){
            Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    val users = PIUserModel()
                    users.ID = cursor.getInt(0)
                    users.username = cursor.getString(1)
                    users.password = cursor.getString(2)
                    users.userRoleID = cursor.getInt(3)
                    userList.add(users)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return userList
    }

    fun addUser(user: PIUserModel):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
        values.put("user_name",user.username)
        values.put("user_password",user.password)
        values.put("user_role_ID",user.userRoleID)

        //insert into table
        val added: Long = db.insert(TABLE_USER,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun updateUser(user: PIUserModel): Boolean{

        val whereClause: String = "user_id=?"
        val whereArgs = arrayOf(user.ID.toString())

        val cv = ContentValues()
        cv.put("user_name",user.username)
        cv.put("user_password",user.password)

        val updated: Long = this.writableDatabase.update(TABLE_USER,cv,whereClause, whereArgs).toLong()

        return (Integer.parseInt("$updated") != -1 )
    }

    fun deleteUser(id:Int): Boolean{
        val whereClause: String = "user_id=?"
        val whereArgs = arrayOf(id.toString())

        val deleted: Long = this.writableDatabase.delete(TABLE_USER,whereClause, whereArgs).toLong()
        return (Integer.parseInt("$deleted") != -1 )
    }

    fun getUserByID(id: Int): PIUserModel{
        val users: PIUserModel = PIUserModel()
        //query
        val selectUser_query = "SELECT * FROM $TABLE_USER WHERE user_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectUser_query,null)
//        db.close()

            cursor?.moveToFirst()
            users.ID = cursor.getInt(0)
            users.username = cursor.getString(1)
            users.password = cursor.getString(2)
            users.userRoleID = cursor.getInt(3)

        cursor.close()
        return users
    }

    // ----- Group Table operation ----- //
    fun addGroup(group: PIGroupModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
        values.put("group_name",group.groupName)

        //insert into table
        val added: Long = db.insert(TABLE_COMPANIES_GROUP,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun getGroups(): List<PIGroupModel>{
        val groupList = ArrayList<PIGroupModel>()
        //query
        val selectGroup_query = "SELECT * FROM $TABLE_COMPANIES_GROUP"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectGroup_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    val groups = PIGroupModel()
                    groups.ID = cursor.getInt(0)
                    groups.groupName = cursor.getString(1)
                    groupList.add(groups)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return groupList

    }

    fun getGroupByID(id: Int): PIGroupModel{
        val groups: PIGroupModel = PIGroupModel()
        //query
        val selectGroup_query = "SELECT * FROM ${TABLE_COMPANIES_GROUP} WHERE group_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectGroup_query,null)
//        db.close()

        cursor?.moveToFirst()
        groups.ID = cursor.getInt(0)
        groups.groupName = cursor.getString(1)

        cursor.close()
        return groups
    }

    fun updateGroup(group:PIGroupModel):Boolean{
        val whereClause: String = "group_id=?"
        val whereArgs = arrayOf(group.ID.toString())

        val cv = ContentValues()
        cv.put("group_name",group.groupName)

        val updated: Long = this.writableDatabase.update(TABLE_COMPANIES_GROUP,cv,whereClause, whereArgs).toLong()

        return (Integer.parseInt("$updated") != -1 )
    }

    fun deleteGroup(id:Int):Boolean{
        val whereClause: String = "group_id=?"
        val whereArgs = arrayOf(id.toString())

        val deleted: Long = this.writableDatabase.delete(TABLE_COMPANIES_GROUP,whereClause, whereArgs).toLong()
        return (Integer.parseInt("$deleted") != -1 )
    }

    // ----- Companies Table operation ----- //
    fun addCompany(company: PICompanyModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
        values.put("company_name",company.companyName)
        values.put("group_ID",company.companyGroupID)

        //insert into table
        val added: Long = db.insert(TABLE_COMPANY,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun getCompanyByID(id:Int): PICompanyModel{
        val company: PICompanyModel = PICompanyModel()
        //query
        val selectCompany_query = "SELECT * FROM ${TABLE_COMPANY} WHERE company_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCompany_query,null)
//        db.close()

        cursor?.moveToFirst()
        company.ID = cursor.getInt(0)
        company.companyName = cursor.getString(1)
        company.companyGroupID = cursor.getInt(2)

        cursor.close()
        return company
    }

    fun updateCompany(company:PICompanyModel):Boolean{
        val whereClause: String = "company_ID=?"
        val whereArgs = arrayOf(company.ID.toString())

        val cv = ContentValues()
        cv.put("company_name",company.companyName)

        val updated: Long = this.writableDatabase.update(TABLE_COMPANY,cv,whereClause, whereArgs).toLong()

        return (Integer.parseInt("$updated") != -1 )
    }

    fun deleteCompany(id:Int):Boolean{
        val whereClause: String = "company_ID=?"
        val whereArgs = arrayOf(id.toString())

        val deleted: Long = this.writableDatabase.delete(TABLE_COMPANY,whereClause, whereArgs).toLong()
        return (Integer.parseInt("$deleted") != -1 )
    }

    fun getCompanies(): List<PICompanyModel>{
        val companyList = ArrayList<PICompanyModel>()
        //query
        val selectCompany_query = "SELECT * FROM $TABLE_COMPANY"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCompany_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    val company = PICompanyModel()
                    company.ID = cursor.getInt(0)
                    company.companyName = cursor.getString(1)
                    company.companyGroupID = cursor.getInt(2)
                    companyList.add(company)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return companyList

    }


    // ----- Product Table operation ----- //
    fun getCompanyNames(): List<String>{
        val companyNamesList = ArrayList<String>()
        //query
        val selectCompany_query = "SELECT company_name FROM $TABLE_COMPANY"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCompany_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    companyNamesList.add(cursor.getString(0))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return companyNamesList
    }

    fun getCompanyIDbyName(companyName: String): Int{
        var companyID: Int = 0
        //query
        val selectCompanyID_query = "SELECT company_ID , company_name FROM $TABLE_COMPANY"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCompanyID_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    if(cursor.getString(1)==companyName){
                        companyID = cursor.getInt(0)
                        cursor.close()
                        return companyID
                    }
                } while (cursor.moveToNext())
            }
        }
        return -1
    }

    fun addProduct(product:PIProductModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
        values.put("product_name",product.productName)
        values.put("product_packing",product.productPacking)
        values.put("product_price",product.productPrice)
        values.put("company_ID",product.productCompanyID)

        //insert into table
        val added: Long = db.insert(TABLE_PRODUCT,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun getProducts():List<PIProductModel>{
        val productList = ArrayList<PIProductModel>()
        //query
        val selectProduct_query = "SELECT * FROM $TABLE_PRODUCT"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectProduct_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    val product = PIProductModel()
                    product.productID = cursor.getInt(0)
                    product.productName = cursor.getString(1)
                    product.productPacking = cursor.getString(2)
                    product.productPrice = cursor.getFloat(3)
                    product.productCompanyID = cursor.getInt(4)
                    productList.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return productList
    }

    fun getProductByID(id: Int): PIProductModel{
        val product: PIProductModel = PIProductModel()
        //query
        val selectProduct_query = "SELECT * FROM ${TABLE_PRODUCT} WHERE product_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectProduct_query,null)
//        db.close()

        cursor?.moveToFirst()
        product.productID = cursor.getInt(0)
        product.productName = cursor.getString(1)
        product.productPacking = cursor.getString(2)
        product.productPrice = cursor.getFloat(3)
        product.productCompanyID = cursor.getInt(4)

        cursor.close()
        return product
    }

    fun updateProduct(product: PIProductModel):Boolean{
        val whereClause: String = "product_ID=?"
        val whereArgs = arrayOf(product.productID.toString())

        val cv = ContentValues()
        cv.put("product_name",product.productName)
        cv.put("product_packing",product.productPacking)
        cv.put("product_price",product.productPrice)

        val updated: Long = this.writableDatabase.update(TABLE_PRODUCT,cv,whereClause, whereArgs).toLong()

        return (Integer.parseInt("$updated") != -1 )
    }

    fun deleteProduct(id: Int): Boolean {
        val whereClause: String = "product_ID=?"
        val whereArgs = arrayOf(id.toString())

        val deleted: Long = this.writableDatabase.delete(TABLE_PRODUCT,whereClause, whereArgs).toLong()
        return (Integer.parseInt("$deleted") != -1 )
    }

    // ----- Customer Table operation ----- //
    fun addCustomer(customer: PICustomerModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
        values.put("customer_name",customer.customerName)
        values.put("customer_address",customer.customerAddress)

        //insert into table
        val added: Long = db.insert(TABLE_CUSTOMER,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun getCustomers():List<PICustomerModel>{
        val customerList = ArrayList<PICustomerModel>()
        //query
        val selectCustomer_query = "SELECT * FROM $TABLE_CUSTOMER"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCustomer_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    val customer = PICustomerModel()
                    customer.customerID = cursor.getInt(0)
                    customer.customerName = cursor.getString(1)
                    customer.customerAddress = cursor.getString(2)
                    customerList.add(customer)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return customerList
    }

    fun getCustomerByID(id: Int): PICustomerModel{
        val customer: PICustomerModel = PICustomerModel()
        //query
        val selectCustomer_query = "SELECT * FROM ${TABLE_CUSTOMER} WHERE customer_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCustomer_query,null)
//        db.close()

        cursor?.moveToFirst()
        customer.customerID = cursor.getInt(0)
        customer.customerName = cursor.getString(1)
        customer.customerAddress = cursor.getString(2)

        cursor.close()
        return customer
    }

    fun updateCustomer(customer: PICustomerModel): Boolean{
        val whereClause: String = "customer_ID=?"
        val whereArgs = arrayOf(customer.customerID.toString())

        val cv = ContentValues()
        cv.put("customer_name",customer.customerName)
        cv.put("customer_address",customer.customerAddress)

        val updated: Long = this.writableDatabase.update(TABLE_CUSTOMER,cv,whereClause, whereArgs).toLong()

        return (Integer.parseInt("$updated") != -1 )
    }

    fun deleteCustomer(id: Int):Boolean{
        val whereClause: String = "customer_ID=?"
        val whereArgs = arrayOf(id.toString())

        val deleted: Long = this.writableDatabase.delete(TABLE_CUSTOMER,whereClause, whereArgs).toLong()
        return (Integer.parseInt("$deleted") != -1 )
    }

    //Order table operation
    fun getCustomerNames(): List<String>{
        val customerNamesList = ArrayList<String>()
        //query
        val selectCustomer_query = "SELECT customer_name FROM $TABLE_CUSTOMER"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectCustomer_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    customerNamesList.add(cursor.getString(0))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return customerNamesList
    }

    fun getProductsNames(): List<String>{
        val productsNamesList = ArrayList<String>()
        //query
        val selectProducts_query = "SELECT product_name FROM $TABLE_PRODUCT"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectProducts_query,null)
//        db.close()
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    productsNamesList.add(cursor.getString(0))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return productsNamesList
    }

    fun getProductDetails(name: String): List<String>{
        var product = ArrayList<String>()
        //query
        val selectProduct_query = "SELECT * FROM ${TABLE_PRODUCT} WHERE product_name=?"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectProduct_query, arrayOf(name))
//        db.close()

        cursor?.moveToFirst()
        //name
        product.add(cursor.getString(1))
        //pack
        product.add(cursor.getString(2))
        //price
        product.add(cursor.getString(3))

        cursor.close()
        return product
    }

    fun addOrderOneline(oneline: PIOrderOnelineModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
//        values.put("oneline_ID",oneline.oo_ID)
        values.put("oneline_product",oneline.oo_product)
        values.put("oneline_productPCK",oneline.oo_product_pck)
        values.put("oneline_QTY",oneline.oo_quantity)
        values.put("oneline_price",oneline.oo_perUnitPrice)
        values.put("oneline_TP",oneline.oo_totalPrice)
        values.put("order_ID",oneline.oo_orderID)

        //insert into table
        val added: Long = db.insert(TABLE_ORDER_ONELINE,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun addOrder(order: PIOrderModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        //put values
        //values.put("order_ID",order.orderID)
        values.put("order_date",order.orderDate)
        values.put("order_TP_price",order.totalPrice)
        values.put("order_salesman",order.salesMan)
        values.put("order_for_customer",order.customerName)

        //insert into table
        val added: Long = db.insert(TABLE_CUSTOMER_ORDER,null,values)
//        db.close()
        return (Integer.parseInt("$added")!=-1)
    }

    fun getOrderByID(id: Int): PIOrderModel{
        val order: PIOrderModel = PIOrderModel()
        //query
        val selectOrder_query = "SELECT * FROM ${TABLE_CUSTOMER_ORDER} WHERE order_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectOrder_query,null)
//        db.close()

        cursor?.moveToFirst()
        order.orderID = cursor.getInt(0)
        order.orderDate = cursor.getString(1)
        order.salesMan = cursor.getString(3)
        order.customerName = cursor.getString(4)
        order.totalPrice = cursor.getFloat(2)

        cursor.close()
        return order
    }

    fun getOrderProductsByID(id: Int): List<PIOrderOnelineModel>{
        val productOneline = ArrayList<PIOrderOnelineModel>()
        //query
        val selectOrder_query = "SELECT * FROM ${TABLE_ORDER_ONELINE} WHERE order_ID = $id"
        //cursor
        val cursor = this.writableDatabase.rawQuery(selectOrder_query,null)
//        db.close()

        //qty,name,price,tp
        if(cursor!=null){
            //Log.d("Cursor","Not null")
            if(cursor.moveToFirst()){
                do {
                    var product= PIOrderOnelineModel()
                    product.oo_quantity = cursor.getInt(3)
                    product.oo_product = cursor.getString(1)
                    product.oo_perUnitPrice = cursor.getFloat(4)
                    product.oo_totalPrice = cursor.getFloat(5)
                    productOneline.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return productOneline
    }
}
