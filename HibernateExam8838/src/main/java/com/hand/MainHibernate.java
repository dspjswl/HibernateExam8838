package com.hand;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

//import org.junit.

/**
 * Created by Administrator on 2015/8/31.
 */
public class MainHibernate {

    public static void main(String[] args) {
        Configuration config = new Configuration().configure("ApplicationContext.xml");
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.print("请输入 FirstName(first_name):");
        Scanner scan1 = new Scanner(System.in);
        String first_name = scan1.nextLine();

        System.out.print("请输入 LastName(last_name):");
        Scanner scan2 = new Scanner(System.in);
        String last_name = scan2.nextLine();

        System.out.print("请输入 Email(email):");
        Scanner scan3 = new Scanner(System.in);
        String email = scan3.nextLine();

        int add_id=1;
        AddressEntity address;
        System.out.print("请输入 Address ID:");
        do {
            Scanner scan4 = new Scanner(System.in);
            add_id = scan4.nextInt();
            address = (AddressEntity) session.get(AddressEntity.class, add_id);
            if(address == null){
                System.out.println("你输入的 Address ID 不存在,请重新输入:");
            }
        }while (address == null);
        String addressname = address.getAddress();

        Date date = new Date();

        try {
            CustomerEntity newcustomer = new CustomerEntity();
            newcustomer.setStoreId((byte) 1);
            newcustomer.setFirstName(first_name);
            newcustomer.setLastName(last_name);
            newcustomer.setEmail(email);
            newcustomer.setAddressId(add_id);
            newcustomer.setCreateDate(date);
            session.save(newcustomer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        List customers = session.createQuery("from com.hand.CustomerEntity").list();
        int lastrecordid=0;
        String lastrecordcreatedate=null;
        for (Iterator iterator =
             customers.iterator(); iterator.hasNext();){
            CustomerEntity ce = (CustomerEntity) iterator.next();
            lastrecordid=ce.getCustomerId();
            lastrecordcreatedate=ce.getCreateDate().toString();
        }
        System.out.println("Before Save");
        System.out.println("已经保存的数据如下:");
        System.out.println("ID:"+lastrecordid);
        System.out.println("FirstName:"+first_name);
        System.out.println("LastName:"+last_name);
        System.out.println("Email:"+email);
        System.out.println("Address:"+addressname);
        System.out.println("Create_date:"+lastrecordcreatedate);

        System.out.print("请输入要删除的 Customer 的 ID:");
        Integer delete_id = 0;
        CustomerEntity ce2 = null;
        do{

            Scanner scan5 = new Scanner(System.in);
            delete_id = scan5.nextInt();
            ce2 = (CustomerEntity)session.get(CustomerEntity.class, delete_id);
            if (ce2 == null)
                System.out.println("你输入的 Customer ID 不存在,请重新输入:");
        }while (ce2 == null);

        Session del_session = sessionFactory.openSession();
        Transaction del_transaction=del_session.beginTransaction();
        try {
            CustomerEntity delete_customer = (CustomerEntity) del_session.get(CustomerEntity.class, delete_id);
            del_session.delete(delete_customer);
            del_transaction.commit();
            System.out.println("你输入的ID为"+delete_id+"的Customer已经删除.");
        } catch (HibernateException e) {
            del_transaction.rollback();
        }finally {
            del_session.close();
        }
    }
}
