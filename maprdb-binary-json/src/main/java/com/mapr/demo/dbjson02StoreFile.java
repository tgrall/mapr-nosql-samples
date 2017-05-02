package com.mapr.demo;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import com.mapr.util.JSONUtil;
import com.mapr.utils.JsonUtils;
import org.apache.hadoop.hbase.util.ByteBufferArray;
import org.apache.hadoop.hbase.util.Bytes;
import org.ojai.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

public class dbjson02StoreFile {

  public static void main(String[] args) throws IOException {

    System.out.println("\n=== START ===");
    Table table = getTable("/apps/user_profiles_json");

    Document doc = MapRDB.newDocument()
            .set("_id","jdoe")
            .set("first_name", "john")
            .set("last_name", "doe");



    // Read file and save it into DB
    RandomAccessFile fileToRead = new RandomAccessFile("/tmp/presentation.pptx", "r");
    FileChannel fChan = fileToRead.getChannel();

    long fSize;
    ByteBuffer mBuf;

    fSize = fChan.size();
    mBuf = ByteBuffer.allocate((int) fSize);
    fChan.read(mBuf);
    mBuf.rewind();

    doc.set("document.presentation", mBuf);
    fChan.close();
    fileToRead.close();




    table.insertOrReplace(doc);
    table.flush();



    // Read data and save to disk
    Document docProjection = table.findById("jdoe");

    ByteBuffer buffToWrite = docProjection.getBinary("document.presentation");

    // Save file to disk
    RandomAccessFile fileTosave = new RandomAccessFile("/tmp/save_from_json.pptx", "rw");
    FileChannel channel = fileTosave.getChannel();
    //buffToWrite.flip();
    channel.write(buffToWrite);
    channel.write(buffToWrite);

    System.out.println("\n=== STOP ===");


  }


  public static Table getTable(String tableName) {

    if (MapRDB.tableExists(tableName)) {
      return MapRDB.getTable(tableName);
    } else {
      return MapRDB.createTable(tableName);
    }


  }


}
