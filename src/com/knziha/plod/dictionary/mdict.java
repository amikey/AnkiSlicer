package com.knziha.plod.dictionary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Adler32;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import org.jvcompress.lzo.MiniLZO;
import org.jvcompress.util.MInt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.test.TouchUtils;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.fenwjian.sdcardutil.RBTree;
import com.fenwjian.sdcardutil.RBTree_additive;
import com.fenwjian.sdcardutil.ResourceMan;
import com.fenwjian.sdcardutil.additiveMyCpr1;
import com.fenwjian.sdcardutil.myCpr;
import com.fenwjian.sdcardutil.ripemd128;
import com.knziha.ankislicer.ui.CMN;
import com.knziha.ankislicer.R;


public class mdict{
    public RelativeLayout rl;
    RelativeLayout.LayoutParams lp;
    //MaterialScrollBar mBar;
    float zoomLevel=1;
    LayoutInflater inflater;
    public static Activity a;
    File f;
    String fn;
    Boolean isFromAsset;
    public String _Dictionary_Name;
    public String _Dictionary_FName;
    public String _Dictionary_FSuffix;
    int _encrypt=0;
    String _encoding="";
    String _passcode = "";
    HashMap<Integer,String[]> _stylesheet = new HashMap<Integer,String[]>();
    float _version;
    int _number_width;
    int _key_block_offset;
    long _record_block_offset;
    long _num_entries;public long getNumberEntrys(){return _num_entries;}
    long _num_key_blocks;
    long _num_record_blocks;
    long maxComRecSize;
    long maxDecompressedSize;

    byte[] _key_block_compressed;
    key_info_struct[] _key_block_info_list;
    record_info_struct[] _record_info_struct_list;
    
    RBTree<myCpr<Integer, Integer>> accumulation_blockId_tree = new RBTree<myCpr<Integer, Integer>>();
    RBTree<myCpr<Long   , Integer>> accumulation_RecordB_tree = new RBTree<myCpr<Long   , Integer>>();
   // RBTree<myCpr<String , Integer>> block_blockId_search_tree = new RBTree<myCpr<String , Integer>>();
    //RBTree<myCprStr<Integer>> block_blockId_search_tree = new RBTree<myCprStr<Integer>>();
    String[] _HTText_blockId_List;
    
    long accumulation_blockId_tree_TIME = 0;
    long block_blockId_search_tree_TIME = 0;
    
    mdictRes mdd;
    

    
    
    public class myCprStr<T2> implements Comparable<myCprStr<T2>>{
        public String key;
        public T2 value;
        public myCprStr(String k,T2 v){
            key=k;value=v;
        }
        public int compareTo(myCprStr<T2> other) {
            if(_encoding.equals("GB18030"))
            try {
                return compareByteArray(this.key.getBytes(_encoding),other.key.getBytes(_encoding));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        //else
                return this.key.compareTo(other.key);
    
        }
        public String toString(){
            return key+"_"+value;
        }
    }    
    //store key_block's summary and itself
    public static class key_info_struct{
		public key_info_struct(String headerKeyText, String tailerKeyText,
    			long key_block_compressed_size_accumulator,
    			long key_block_decompressed_size) {
    		this.headerKeyText=headerKeyText;
    		this.tailerKeyText=tailerKeyText;		
    		this.key_block_compressed_size_accumulator=key_block_compressed_size_accumulator;		
    		this.key_block_decompressed_size=key_block_decompressed_size;		
    	}
    	public key_info_struct(long num_entries_,long num_entries_accumulator_) {
    		num_entries=num_entries_;
    		num_entries_accumulator=num_entries_accumulator_;
        }
		public String headerKeyText;
    	public String tailerKeyText;
    	public long key_block_compressed_size_accumulator;
    	public long key_block_decompressed_size;
        public long num_entries;
        public long num_entries_accumulator;
        public String[] keys;
        public long[] key_offsets;
        public void ini(){
            keys =new String[(int) num_entries];
            key_offsets =new long[(int) num_entries];
        }
    }
    //store record_block's summary
    public static class record_info_struct{
    	public record_info_struct(long _compressed_size,long _compressed_size_accumulator,long _decompressed_size,long _decompressed_size_accumulator) {
    		 compressed_size=                  _compressed_size;
             compressed_size_accumulator=      _compressed_size_accumulator;
             decompressed_size=                _decompressed_size;
             decompressed_size_accumulator=    _decompressed_size_accumulator;
        
    	}
        public long compressed_size;
        public long compressed_size_accumulator;
    	public long decompressed_size;
    	public long decompressed_size_accumulator;
        public void ini(){

        }
    }	
	
	static byte[] _fast_decrypt(byte[] data,byte[] key){ 
	    long previous = 0x36;
	    for(int i=0;i<data.length;i++){
	    	//INCONGRUENT CONVERTION FROM byte to int
	    	int ddd = data[i]&0xff;
	    	long t = (ddd >> 4 | ddd << 4) & 0xff;
	        t = t ^ previous ^ (i & 0xff) ^ (key[(i % key.length)]&0xff);
	        previous = ddd;
	        data[i] = (byte) t;
        }
	    return data;
    }
	
	static byte[] _mdx_decrypt(byte[] comp_block) throws IOException{
		ByteArrayOutputStream data = new ByteArrayOutputStream() ;
		data.write(comp_block,4,4);
		data.write(ripemd128.packIntLE(0x3695));
	    byte[]  key = ripemd128.ripemd128(data.toByteArray());
	    data.reset();
	    data.write(comp_block,0,8);
	    byte[] comp_block2 = new byte[comp_block.length-8];
	    System.arraycopy(comp_block, 8, comp_block2, 0, comp_block.length-8);
	    data.write(_fast_decrypt(comp_block2, key));
	    return data.toByteArray();
    }

	boolean isWebHidden=false;
	boolean isZooming=false;
	public float flingDelta = 100;
	float svx1 = 0,svy1=0,svx2=0,svy2=0;
	float svFlingvelocityX;
	//public Handler mHandler;
	Timer timer = new Timer();
	class tk extends TimerTask{
		@Override
		public void run() {
			//mHandler.sendEmptyMessage(1);
		}
		
	};
    //构造
	public mdict(String _fn,Boolean _isFromAsset) throws IOException{
        isFromAsset=_isFromAsset;
        fn = _fn;

//![]File in
    	//byte[] asd = new byte[]{'s',2,3,4,1,2,3,4,1,2,3,4};NameOfPlants.mdx 简明英汉汉英词典.mdx
    	//File f = new File("/sdcard/BlueDict/Dicts/简明英汉汉英词典.mdx");
    	DataInputStream data_in;
    	//if(!isFromAsset){
        	f = new File(fn);
        	_Dictionary_FName = f.getName();
        	int tmpIdx = _Dictionary_FName.lastIndexOf(".");
        	_Dictionary_FSuffix = _Dictionary_FName.substring(tmpIdx+1);
        	_Dictionary_FName = _Dictionary_FName.substring(0,tmpIdx);
	    	String fnTMP = f.getName();
	    	File f2 = new File(f.getParentFile().getAbsolutePath()+"/"+fnTMP.substring(0,fnTMP.lastIndexOf("."))+".mdd");
	    	if(f2.exists()){
	    		mdd=new mdictRes(f2.getAbsolutePath(),false);
	    	}
	    	data_in =new DataInputStream(new FileInputStream(f));	
    	//}else{}
    	//FileInputStream data_in =new FileInputStream(f);	
    	
//![0]read_header 
    	// number of bytes of header text
    	byte[] itemBuf = new byte[4];
		data_in.read(itemBuf, 0, 4);
    	int header_bytes_size =getInt(itemBuf[0],itemBuf[1],itemBuf[2],itemBuf[3]);
    	byte[] header_bytes = new byte[header_bytes_size];
    	data_in.read(header_bytes,0, header_bytes_size); 
		// 4 bytes: adler32 checksum of header, in little endian
		itemBuf = new byte[4];
		data_in.read(itemBuf, 0, 4);
    	int alder32 = getInt(itemBuf[3],itemBuf[2],itemBuf[1],itemBuf[0]);
		assert alder32 == (BU.calcChecksum(header_bytes)& 0xffffffff);
		_key_block_offset = 4 + header_bytes_size + 4;
		//data_in.close();
		//不必关闭文件流
		
		Pattern re = Pattern.compile("(\\w+)=\"(.*?)\"",Pattern.DOTALL);
		Matcher m = re.matcher(new String(header_bytes,"UTF-16LE"));
		HashMap<String,String> header_tag = new HashMap<String,String>();
		while(m.find()) {
			//for(int i=0;i<=m.groupCount();i++)
	        // System.out.println("Found value: " + m.group(i)); 
			header_tag.put(m.group(1), m.group(2));
	      }
		if(header_tag.containsKey("Title"))
			_Dictionary_Name=header_tag.get("Title");

		_encoding = header_tag.get("Encoding");
        // GB18030 > GBK > GB2312
        if(_encoding.equals("GBK")|| _encoding.equals("GB2312"))
        	_encoding = "GB18030";
        if(_encoding.equals("UTF-16"))
        	_encoding = "UTF-16LE"; //INCONGRUENT java charset          
        if (_encoding.equals(emptyStr))
        	_encoding = "UTF-8";
        
		if(!header_tag.containsKey("Encrypted") || header_tag.get("Encrypted") == "0" || header_tag.get("Encrypted").equals("No"))
            _encrypt = 0;
		else if(header_tag.get("Encrypted") == "1")
            _encrypt = 1;
        else
            _encrypt = Integer.valueOf(header_tag.get("Encrypted"));
        
        if(header_tag.containsKey("StyleSheet")){
            String[] lines = header_tag.get("StyleSheet").split("[\r\n \r \n]");
            for(int i=0;i<=lines.length-3;i+=3)
                _stylesheet.put(i,new String[]{lines[i+1],lines[i+2]});
        }
        _version = Float.valueOf(header_tag.get("GeneratedByEngineVersion"));
        if(_version < 2.0)
            _number_width = 4;
        else
            _number_width = 8;
//![0]HEADER 分析完毕 
//_read_keys START
        //size (in bytes) of previous 5 numbers (can be encrypted)
        int num_bytes;
        if(_version >= 2)
            num_bytes = 8 * 5;
        else
            num_bytes = 4 * 4;
		itemBuf = new byte[num_bytes];

			data_in.read(itemBuf, 0, num_bytes);

        ByteBuffer sf = ByteBuffer.wrap(itemBuf);
        //TODO: pureSalsa20.py decryption
        if(_encrypt==1){if(_passcode=="") throw new IllegalArgumentException("_passcode未输入");}
        _num_key_blocks = _read_number(sf);
        _num_entries = _read_number(sf);
        if(_version >= 2.0){long key_block_info_decomp_size = _read_number(sf);}
        
        long key_block_info_size = _read_number(sf);
        long key_block_size = _read_number(sf);
        
        // adler checksum of previous 5 numbers
        if(_version >= 2.0){
            int adler32 = BU.calcChecksum(itemBuf);
    		itemBuf = new byte[4];
    		data_in.read(itemBuf, 0, 4);
            assert adler32 == (getInt(itemBuf[0],itemBuf[1],itemBuf[2],itemBuf[3])& 0xffffffff);
        }
        
        // read key block info, which comprises each key block's starting&&ending words' textSize&&shrinkedText、compressed && decompressed size
		itemBuf = new byte[(int) key_block_info_size];
		data_in.read(itemBuf, 0, (int) key_block_info_size);
        _key_block_info_list = _decode_key_block_info(itemBuf); // key_block_info_list
        assert(_num_key_blocks == _key_block_info_list.length);
        System.out.println("num_key_blocks ="+_num_key_blocks);  
        // read key block
        if(_version<2)
            _record_block_offset = _key_block_offset+4 * 4+key_block_info_size+key_block_size;
        else
        	_record_block_offset = _key_block_offset+num_bytes+4+key_block_info_size+key_block_size;
//_read_keys END
		//System.out.println(block_blockId_search_tree.sxing(new myCprStr("rmt",1)) );
		//System.out.println(block_blockId_search_tree.sxing(new myCprStr("rmt",1)).getKey().value );
		System.out.println("accumulation_blockId_tree_TIME 建树时间="+accumulation_blockId_tree_TIME);
		//System.out.println("block_blockId_search_tree_TIME 建树时间="+block_blockId_search_tree_TIME); 
        
        _key_block_compressed = new byte[(int) key_block_size];
		data_in.read(_key_block_compressed, 0, (int) key_block_size);
		
//Decode record block header
long start = System.currentTimeMillis();
		DataInputStream data_in1;
		//if(!isFromAsset){
			data_in1 = new DataInputStream(new FileInputStream(f));
		//}else{}
        data_in1.skipBytes((int) _record_block_offset);
        _num_record_blocks = _read_number(data_in1);

        long num_entries = _read_number(data_in1);
        assert(num_entries == _num_entries);
        long record_block_info_size = _read_number(data_in1);
        long record_block_size = _read_number(data_in1);
        //record block info section
        _record_info_struct_list = new record_info_struct[(int) _num_record_blocks];
        int size_counter = 0;
        long compressed_size_accumulator = 0;
        long decompressed_size_accumulator = 0;
//Log.e("hahhaha","record_block_info_size:"+record_block_info_size+"_num_record_blocks:"+_num_record_blocks);

        /*faster:batch read-in strategy*/
		byte[] numers = new byte[(int) record_block_info_size];
		data_in1.read(numers);
		for(int i=0;i<_num_record_blocks;i++){
            //long compressed_size = _read_number(data_in1);
            //long decompressed_size = _read_number(data_in1);
			long compressed_size = _version>=2?toLong(numers,(int) (i*16)):toInt(numers,(int) (i*8));
	        long decompressed_size = _version>=2?toLong(numers,(int) (i*16+8)):toInt(numers,(int) (i*8+4));
            maxComRecSize = Math.max(maxComRecSize, compressed_size);
            maxDecompressedSize = Math.max(maxDecompressedSize, decompressed_size);
            _record_info_struct_list[i] = new record_info_struct(compressed_size, compressed_size_accumulator, decompressed_size, decompressed_size_accumulator);
            accumulation_RecordB_tree.insert(new myCpr<Long, Integer>(decompressed_size_accumulator,i));
            compressed_size_accumulator+=compressed_size;
            decompressed_size_accumulator+=decompressed_size;
            size_counter += _number_width * 2;
		}
		postIni();
Log.e("time Decode record block header",(System.currentTimeMillis()-start)+"");
		//toolbar.setTitle(this._Dictionary_FName.split(".mdx")[0]);
	}
	//构造结束
	private void postIni() {
		record_block = new byte[(int) maxDecompressedSize];		
	}
	
	


	

    
	private key_info_struct[] _decode_key_block_info(byte[] key_block_info_compressed) throws UnsupportedEncodingException {
        key_info_struct[] key_block_info_list = new key_info_struct[(int) _num_key_blocks];
        _HTText_blockId_List = new String[(int) _num_key_blocks];
        byte[] key_block_info;
    	if(_version >= 2)
        {   //zlib压缩
    		byte[] asd = new byte[]{key_block_info_compressed[0],key_block_info_compressed[1],key_block_info_compressed[2],key_block_info_compressed[3]};
    		assert(new String(asd).equals(new String(new byte[]{2,0,0,0})));
            //处理 Ripe128md 加密的 key_block_info
    		if(_encrypt==2){try{
                key_block_info_compressed = _mdx_decrypt(key_block_info_compressed);
                } catch (IOException e) {e.printStackTrace();}}
			//!!!getInt CAN BE NEGTIVE ,INCONGRUENT to python CODE
    		//!!!MAY HAVE BUG
            int adler32 = getInt(key_block_info_compressed[4],key_block_info_compressed[5],key_block_info_compressed[6],key_block_info_compressed[7]);
            key_block_info = zlib_decompress(key_block_info_compressed,8);
            assert(adler32 == (BU.calcChecksum(key_block_info) ));
        }
        else
            key_block_info = key_block_info_compressed;
    	// decoding……
        ByteBuffer sf = ByteBuffer.wrap(key_block_info);
        byte[] textbuffer = new byte[1];
        String headerKeyText,tailerKeyText;
        long key_block_compressed_size = 0,key_block_decompressed_size = 0;
        long start1,end1,start2,end2;
        int accumulation_ = 0,num_entries=0;//how many entries before one certain block.for construction of a list.
        int byte_width = 2,text_term = 1;//DECREPTING version1...bcz I am lazy
        if(_version<2)
        {byte_width = 1;text_term = 0;}
        //遍历blocks
        for(int i=0;i<key_block_info_list.length;i++){
            // number of entries in current key block

            start1=System.currentTimeMillis(); //获取开始时间  
        	accumulation_blockId_tree.insert(new myCpr<Integer, Integer>(accumulation_,i));
            end1=System.currentTimeMillis(); //获取结束时间
            accumulation_blockId_tree_TIME+=end1-start1;

            if(_version<2)
            key_block_info_list[i] = new key_info_struct(sf.getInt(),accumulation_);
            else
            key_block_info_list[i] = new key_info_struct(sf.getLong(),accumulation_);

            key_info_struct infoI = key_block_info_list[i];
            accumulation_ += infoI.num_entries;
            
            //![0] head word text
            int text_head_size;
            if(_version<2)
            	text_head_size = sf.get();
        	else
        		text_head_size = sf.getChar();
            if(!_encoding.startsWith("UTF-16")){
                textbuffer = new byte[text_head_size];
                sf.get(textbuffer, 0,text_head_size);
                if(_version>=2) sf.get();                
            }else{
                textbuffer = new byte[text_head_size*2];
                sf.get(textbuffer, 0, text_head_size*2);
                if(_version>=2)
                sf.get();if(_version>=2) sf.get();                
            }
            infoI.headerKeyText = new String(textbuffer,_encoding);
            //System.out.println("headerKeyText"+infoI.headerKeyText);

            //![1]  tail word text
            int text_tail_size;
            if(_version<2)
            	text_tail_size = sf.get();
        	else
        		text_tail_size = sf.getChar();
            if(!_encoding.startsWith("UTF-16")){
                textbuffer = new byte[text_tail_size];
                sf.get(textbuffer, 0, text_tail_size);
                if(_version>=2) sf.get();         
            }else{
                textbuffer = new byte[text_tail_size*2];
                sf.get(textbuffer, 0, text_tail_size*2);
                if(_version>=2)
                sf.get();if(_version>=2) sf.get();             
            }
            infoI.tailerKeyText = new String(textbuffer,_encoding);

            infoI.key_block_compressed_size_accumulator = key_block_compressed_size;
            if(_version<2){
            	key_block_compressed_size += sf.getInt();
            	infoI.key_block_decompressed_size = sf.getInt();
            }else{
            	key_block_compressed_size += sf.getLong();
            	infoI.key_block_decompressed_size = sf.getLong();
            }
            //start2=System.currentTimeMillis(); //获取开始时间  
            //_HTText_blockId_List[i] = infoI.headerKeyText;
            _HTText_blockId_List[i] = infoI.headerKeyText;
            //block_blockId_search_tree.insert(new myCprStr<Integer>(infoI.headerKeyText,i));
            //block_blockId_search_tree.insert(new myCpr<String, Integer>(tailerKeyText,i));
            //end2=System.currentTimeMillis(); //获取结束时间
            //block_blockId_search_tree_TIME+=end2-start2;
        }
        //assert(accumulation_ == self._num_entries)
        return key_block_info_list;
	}
 
    int rec_decompressed_size;
	private byte[] record_block;
	
	
    public String getRecordAt(int position) throws IOException {
    	if(position<0||position>=_num_entries) return null;
        int blockId = accumulation_blockId_tree.xxing(new myCpr(position,1)).getKey().value;
        key_info_struct infoI = _key_block_info_list[blockId];
        long start = infoI.key_block_compressed_size_accumulator;
        long compressedSize;
        prepareItemByKeyInfo(infoI,blockId);
        String[] key_list = infoI.keys;
//decode record block
        // actual record block data
        int i = (int) (position-infoI.num_entries_accumulator);
        int Rinfo_id = accumulation_RecordB_tree.xxing(new myCpr(infoI.key_offsets[i],1)).getKey().value;
        record_info_struct RinfoI = _record_info_struct_list[Rinfo_id];
        
        prepareRecordBlock(RinfoI,Rinfo_id);
        
            
        // split record block according to the offset info from key block
        //String key_text = key_list[i];
        long record_start = infoI.key_offsets[i]-RinfoI.decompressed_size_accumulator;
        long record_end;
        if (i < key_list.length-1){
        	record_end = infoI.key_offsets[i+1]-RinfoI.decompressed_size_accumulator; 	
        }//TODO construct a margin checker
        else{
        	if(blockId+1<_key_block_info_list.length) {
        		prepareItemByKeyInfo(null,blockId+1);//没办法只好重新准备一个咯
        		//难道还能根据text末尾的0a 0d 00来分？不大好吧、
            	record_end = _key_block_info_list[blockId+1].key_offsets[0]-RinfoI.decompressed_size_accumulator;
        	}else
        		record_end = rec_decompressed_size;
        	//CMN.show(record_block.length+":"+compressed_size+":"+decompressed_size);
        }
        //CMN.show(record_start+"!"+record_end);
        byte[] record = new byte[(int) (record_end-record_start)]; 
        //CMN.show(record.length+":"+record_block.length+":"+(record_start));
        System.arraycopy(record_block, (int) (record_start), record, 0, record.length);
        // convert to utf-8
        String record_str = new String(record,_encoding);
        // substitute styles
        //if self._substyle and self._stylesheet:
        //    record = self._substitute_stylesheet(record);
        return	record_str;        
    }
  
    
    int prepared_RecordBlock_ID=-1;
    private void prepareRecordBlock(record_info_struct RinfoI, int Rinfo_id) throws IOException {
    	if(prepared_RecordBlock_ID==Rinfo_id)
    		return;
    	if(RinfoI==null)
    		RinfoI = _record_info_struct_list[Rinfo_id];
    	DataInputStream data_in = new DataInputStream(new FileInputStream(f));
        // record block info section
        data_in.skipBytes( (int) (_record_block_offset+_number_width*4+_num_record_blocks*2*_number_width));
        
        
        data_in.skipBytes((int) RinfoI.compressed_size_accumulator);
        //whole section of record_blocks;
       // for(int i123=0; i123<record_block_info_list.size(); i123++){
        	int compressed_size = (int) RinfoI.compressed_size;
        	int decompressed_size = rec_decompressed_size = (int) RinfoI.decompressed_size;//用于验证
        	byte[] record_block_compressed = new byte[(int) compressed_size];
        	//System.out.println(compressed_size) ;
        	//System.out.println(decompressed_size) ;
        	data_in.read(record_block_compressed);
            // 4 bytes indicates block compression type
        	byte[] record_block_type = new byte[4];
        	System.arraycopy(record_block_compressed, 0, record_block_type, 0, 4);
        	String record_block_type_str = new String(record_block_type);
        	//ripemd128.printBytes(record_block_type);
        	// 4 bytes adler checksum of uncompressed content
        	ByteBuffer sf1 = ByteBuffer.wrap(record_block_compressed);
            int adler32 = sf1.order(ByteOrder.BIG_ENDIAN).getInt(4);
            adler32 = sf1.order(ByteOrder.BIG_ENDIAN).getInt(4);
            // no compression
            if(record_block_type_str.equals(new String(new byte[]{0,0,0,0}))){
            	System.arraycopy(record_block_compressed, 8, record_block, 0, compressed_size-8);
            }
            // lzo compression
            else if(record_block_type_str.equals(new String(new byte[]{1,0,0,0}))){
                //record_block = new byte[ decompressed_size];
                MInt len = new MInt(decompressed_size);
                byte[] arraytmp = new byte[ compressed_size];        
                System.arraycopy(record_block_compressed, 8, arraytmp, 0,(int) (compressed_size-8));
                MiniLZO.lzo1x_decompress(arraytmp,compressed_size,record_block,len);
            }
            // zlib compression
            else if(record_block_type_str.equals(new String(new byte[]{02,00,00,00}))){
                // decompress
                // record_block = zlib_decompress(record_block_compressed,8);
                Inflater inf = new Inflater();
                inf.setInput(record_block_compressed,8,compressed_size-8);
                try {
					int ret = inf.inflate(record_block,0,decompressed_size);
				} catch (DataFormatException e) {
					e.printStackTrace();
				}  
            }
            // notice not that adler32 return signed value
            
            //CMN.show(adler32+"'''"+(BU.calcChecksum(record_block,0,decompressed_size)));
            assert(adler32 == (BU.calcChecksum(record_block,0,decompressed_size) ));
            //assert(record_block.length == decompressed_size );
 //当前内容块解压完毕		
            prepared_RecordBlock_ID=Rinfo_id;
	}
    
    
    //到底要不要将key entrys存储起来？？
    public void prepareItemByKeyInfo(key_info_struct infoI,int blockId){
        if(infoI==null)
        	infoI = _key_block_info_list[blockId];
    	if(infoI.keys==null){
        	long st=System.currentTimeMillis();
            long start = infoI.key_block_compressed_size_accumulator;
            long compressedSize;
        	infoI.ini();
            byte[] key_block = new byte[1];
            if(blockId==_key_block_info_list.length-1)
                compressedSize = _key_block_compressed.length - _key_block_info_list[_key_block_info_list.length-1].key_block_compressed_size_accumulator;
            else
                compressedSize = _key_block_info_list[blockId+1].key_block_compressed_size_accumulator-infoI.key_block_compressed_size_accumulator;
            
            String key_block_compression_type = new String(new byte[]{_key_block_compressed[(int) start],_key_block_compressed[(int) (start+1)],_key_block_compressed[(int) (start+2)],_key_block_compressed[(int) (start+3)]});
            int adler32 = getInt(_key_block_compressed[(int) (start+4)],_key_block_compressed[(int) (start+5)],_key_block_compressed[(int) (start+6)],_key_block_compressed[(int) (start+7)]);
            if(key_block_compression_type.equals(new String(new byte[]{0,0,0,0}))){
                //无需解压
                System.out.println("no compress!");
                key_block = new byte[(int) (_key_block_compressed.length-start-8)];
                System.arraycopy(_key_block_compressed, (int)(start+8), key_block, 0,(int) (_key_block_compressed.length-start-8));
            }else if(key_block_compression_type.equals(new String(new byte[]{1,0,0,0})))
            {
                //key_block = lzo_decompress(_key_block_compressed,(int) (start+_number_width),(int)(compressedSize-_number_width));
            	MInt len = new MInt((int) infoI.key_block_decompressed_size);
            	key_block = new byte[len.v];
                byte[] arraytmp = new byte[(int) compressedSize];
                //show(arraytmp.length+"哈哈哈"+compressedSize);
                System.arraycopy(_key_block_compressed, (int)(start+8), arraytmp, 0,(int) (compressedSize-8));
                //CMN.show("_key_block_compressed");
                //ripemd128.printBytes(_key_block_compressed,(int) (start+8),(int)(compressedSize-8));
                //CMN.show(infoI.key_block_decompressed_size+"~"+infoI.key_block_compressed_size);
                //CMN.show(infoI.key_block_decompressed_size+"~"+compressedSize);
                MiniLZO.lzo1x_decompress(arraytmp,arraytmp.length,key_block,len);
                //System.out.println("look up LZO decompressing key blocks done!");
            }
            else if(key_block_compression_type.equals(new String(new byte[]{02,00,00,00}))){
                //key_block = zlib_decompress(_key_block_compressed,(int) (start+8),(int)(compressedSize-8));
                //System.out.println("zip!");
                //System.out.println("zip!");
                Inflater inf = new Inflater();
                inf.setInput(_key_block_compressed,(int) (start+8),(int)(compressedSize-8));
                key_block = new byte[(int) infoI.key_block_decompressed_size];
                try {
					int ret = inf.inflate(key_block,0,(int)(infoI.key_block_decompressed_size));
				} catch (DataFormatException e) {e.printStackTrace();}
                
            }
            /*!!spliting curr Key block*/
            int key_start_index = 0;
            String delimiter;
            int width = 0,i1=0,key_end_index=0;
            int keyCounter = 0;
            ByteBuffer sf = ByteBuffer.wrap(key_block);//must outside of while...
            /*主要耗时步骤
		            主要耗时步骤
		            主要耗时步骤*/
            
            while(key_start_index < key_block.length){
            	long key_id;
            	if(_version<2)
            		key_id = sf.getInt(key_start_index);//Key_ID
            	else
            		key_id = sf.getLong(key_start_index);//Key_ID
                //show("key_id"+key_id);
                if(_encoding.startsWith("UTF-16")){//TODO optimize
                    width = 2;
                    key_end_index = key_start_index + _number_width;  
                    while(i1<key_block.length){
                    	if(key_block[key_end_index]==0 && key_block[key_end_index+1]==0)
                    		break;
                    	key_end_index+=width;
                    }
                }else{
                    width = 1;
                    key_end_index = key_start_index + _number_width;  
                    while(i1<key_block.length){
                    	//CMN.show(key_block.length+"_"+key_end_index);
                    	if(key_block[key_end_index]==0)
                    		break;
                    	key_end_index+=width;
                    }
                }

                //show("key_start_index"+key_start_index);
                byte[] arraytmp = new byte[key_end_index-(key_start_index+_number_width)];
                System.arraycopy(key_block,key_start_index+_number_width, arraytmp, 0,arraytmp.length);
                
                String key_text = null;
				try {
					key_text = new String(arraytmp,_encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				//CMN.show(keyCounter+":::"+key_text);
                key_start_index = key_end_index + width;
                //CMN.show(infoI.keys.length+"~~~"+keyCounter+"~~~"+infoI.num_entries);
                infoI.keys[keyCounter]=key_text;
                
                infoI.key_offsets[keyCounter]=key_id;
                keyCounter++;
            }
            //System.out.println("耗时"+(st-System.currentTimeMillis()));

            assert(adler32 == (BU.calcChecksum(key_block)));
            //System.out.println("建key表时间"+(e-st));
        }
    }
          
    public void fetch_keyBlocksHeaderTextKeyID(){
    	int blockId = 0;
    	keyBlocksHeaderTextKeyID = new long[(int)_num_key_blocks];
    	for(key_info_struct infoI:_key_block_info_list){
            long start = infoI.key_block_compressed_size_accumulator;
            long compressedSize;
            byte[] key_block = new byte[1];
            if(blockId==_key_block_info_list.length-1)
                compressedSize = _key_block_compressed.length - _key_block_info_list[_key_block_info_list.length-1].key_block_compressed_size_accumulator;
            else
                compressedSize = _key_block_info_list[blockId+1].key_block_compressed_size_accumulator-infoI.key_block_compressed_size_accumulator;
            
            byte[] key_block_compression_type = new byte[]{_key_block_compressed[(int) start],_key_block_compressed[(int) (start+1)],_key_block_compressed[(int) (start+2)],_key_block_compressed[(int) (start+3)]};
            if(compareByteArrayIsPara(key_block_compression_type, _zero4)){
                //无需解压
                System.out.println("no compress!");
                key_block = new byte[(int) (_key_block_compressed.length-start-8)];
                System.arraycopy(_key_block_compressed, (int)(start+8), key_block, 0,(int) (_key_block_compressed.length-start-8));
            }else if(compareByteArrayIsPara(key_block_compression_type, _1zero3))
            {
                //key_block = lzo_decompress(_key_block_compressed,(int) (start+_number_width),(int)(compressedSize-_number_width));
            	MInt len = new MInt((int) infoI.key_block_decompressed_size);
            	key_block = new byte[len.v];
                byte[] arraytmp = new byte[(int) compressedSize];
                show(arraytmp.length+"哈哈哈"+compressedSize);
                System.arraycopy(_key_block_compressed, (int)(start+8), arraytmp, 0,(int) (compressedSize-8));
            	MiniLZO.lzo1x_decompress(arraytmp,arraytmp.length,key_block,len);
                //System.out.println("look up LZO decompressing key blocks done!");
            }
            else if(compareByteArrayIsPara(key_block_compression_type, _2zero3)){
                key_block = zlib_decompress(_key_block_compressed,(int) (start+8),(int)(compressedSize-8));
                //System.out.println("zip!");
            }
            //!!spliting curr Key block
            //ByteBuffer sf = ByteBuffer.wrap(key_block);//must outside of while...
            /*主要耗时步骤
		            主要耗时步骤
		            主要耗时步骤*/
            
            	if(_version<2)
            		keyBlocksHeaderTextKeyID[blockId] = getInt(key_block[0],key_block[0],key_block[0],key_block[0]);
            	else
            		//keyBlocksHeaderTextKeyID[blockId] = getLong(key_block[0],key_block[1],key_block[2],key_block[3],key_block[4],key_block[5],key_block[6],key_block[7]);
        		keyBlocksHeaderTextKeyID[blockId] = getLong(key_block);

                blockId++;


        }
    }
     
    
    long[] keyBlocksHeaderTextKeyID;

    volatile int thread_number_count = 0;

    //for list view
	public String getEntryAt(int position) {
        int blockId = accumulation_blockId_tree.xxing(new myCpr(position,1)).getKey().value;
        key_info_struct infoI = _key_block_info_list[blockId];
        prepareItemByKeyInfo(infoI,blockId);
        return infoI.keys[(int) (position-infoI.num_entries_accumulator)];
		
	}
	public int split_keys_thread_number;
	//public ArrayList<myCpr<String,Integer>>[] combining_search_tree;
	public ArrayList<additiveMyCpr1>[] combining_search_tree2;

    public void size_confined_lookUp(String keyword,
            RBTree_additive combining_search_tree, int SelfAtIdx, int theta) 
			throws UnsupportedEncodingException 
		{
    	keyword = keyword.toLowerCase().replaceAll(replaceReg,emptyStr);
        //int blockId = block_blockId_search_tree.sxing(new myCprStr(keyword,1)).getKey().value;
        int blockId = binary_find_closest(_HTText_blockId_List,keyword);
        
        //int blockId = binary_find_closest_HTText_blockId_List(keyword);
        //show("blockId:"+blockId);
        int res;
        if(_encoding.equals("GB18030"))
        while(blockId!=0 &&  compareByteArray(_key_block_info_list[blockId-1].tailerKeyText.getBytes(),keyword.getBytes())>=0)
        	blockId--;
        else
        while(blockId!=0 &&  _key_block_info_list[blockId-1].tailerKeyText.compareTo(keyword)>=0)
        	blockId--;
        key_info_struct infoI = _key_block_info_list[blockId];

        prepareItemByKeyInfo(infoI,blockId);
        
        if(_encoding.equals("GB18030")){
            res = binary_find_closest2(infoI.keys,keyword);//keyword
        }else
        	res = binary_find_closest(infoI.keys,keyword);//keyword

        if (res==-1){
        	System.out.println("search failed!"+keyword);
        	return;
        }
        else{
        	//String KeyText= infoI.keys[res];
        	//show("match key "+KeyText+" at "+res);
        	int end = Math.min(res+theta, infoI.keys.length-1);
        	int yuShu = theta-(end-res);
        	//show(" end key "+infoI.keys[end]+" at "+end+"yuShu"+yuShu);
        	int start = res;
        	if(!infoI.keys[end].startsWith(keyword))
        	while(start<end)//进入二分法
        	if(infoI.keys[end].startsWith(keyword)){
        		int dist = end-start+1;
        		start = end;
        		end = end+(dist)/2;//扩大
        	}else{
        		end = (start+end)/2;//缩小
        	}
        	for(int i=res;i<=end;i++){
        		combining_search_tree.insert(infoI.keys[i],SelfAtIdx,(int)(infoI.num_entries_accumulator+i));
        	}
        	//show("pre match end key "+infoI.keys[end]+" at "+end);
        	while(yuShu>0){//要进入下一个key_block查询
        		if(++blockId>=_key_block_info_list.length) break;
        		start = 0;
                key_info_struct infoIi = _key_block_info_list[blockId];
                prepareItemByKeyInfo(infoIi,blockId);
                if(!infoIi.keys[0].startsWith(keyword)) break;
            	//show("2 start key "+infoIi.keys[start]+" at "+start);
            	end = Math.min(yuShu-1, infoIi.keys.length-1);
            	yuShu = yuShu-infoIi.keys.length;//如果大于零，则仍然需要查询下一个词块
            	//show("2 end key "+infoIi.keys[end]+" at "+end);
            	if(!infoIi.keys[end].startsWith(keyword))
            	while(start<end)//进入二分法
                	if(infoIi.keys[end].startsWith(keyword)){
                		int dist = end-start+1;
                		start = end;
                		end = end+(dist)/2;//扩大
                	}else{
                		end = (start+end)/2;//缩小
                	}
                	//show("2 match end key "+infoIi.keys[end]+" at "+end);
                	for(int i=0;i<=end;i++){
                		combining_search_tree.insert(infoIi.keys[i],SelfAtIdx,(int)(infoIi.num_entries_accumulator+i));
                	}
        	}
        	
        	
    }   
		
	}   
    public int lookUp(String k) throws UnsupportedEncodingException {
    	return lookUp(k,false);
    }
    public int lookUp(String keyword,boolean isLoose)
                            throws UnsupportedEncodingException
    {
    	keyword = keyword.toLowerCase().replaceAll(replaceReg,emptyStr);
        //int blockId = block_blockId_search_tree.sxing(new myCprStr(keyword,1)).getKey().value;
        int blockId = binary_find_closest_loose(_HTText_blockId_List,keyword);

        if(blockId==-1) return blockId;
        if(_encoding.equals("GB18030"))
        while(blockId!=0 &&  compareByteArray(_key_block_info_list[blockId-1].tailerKeyText.getBytes("GB18030"),keyword.getBytes("GB18030"))>=0)
          	blockId--;
        else
        while(blockId!=0 &&  _key_block_info_list[blockId-1].tailerKeyText.compareTo(keyword)>=0)
          	blockId--;
        key_info_struct infoI = _key_block_info_list[blockId];
        prepareItemByKeyInfo(infoI,blockId);
        int res;
        if(_encoding.equals("GB18030")){
            res = binary_find_closest2(infoI.keys,keyword,isLoose);//keyword
        }else
        	res = binary_find_closest(infoI.keys,keyword,isLoose);//keyword
        
        if (res==-1){
        	System.out.println("search failed!");
            return -1;
        }
        else{
        	String KeyText= infoI.keys[res];
        	long lvOffset = infoI.num_entries_accumulator+res;
        	long wjOffset = infoI.key_block_compressed_size_accumulator+infoI.key_offsets[res];
        	//System.out.println("key: "+KeyText);
        	return (int) lvOffset;
        }
    }

    public int  binary_find_closest(String[] a,String v) {
    	return binary_find_closest(a,v,false);
    }
    public int  binary_find_closest2(String[] a,String v) throws UnsupportedEncodingException {
    	return binary_find_closest2(a,v,false);
    }
    public int  binary_find_closest(String[] array,String val,boolean isLoose){
    	//TODO 2018.5.12 从P.L.O.D中粘贴代码过来，尚未修改?
    	int middle = 0;
    	int iLen = array.length;
    	int low=0,high=iLen-1;
    	if(array==null || iLen<1){
    		return -1;
    	}
    	
    	if(val.compareTo(array[0].toLowerCase().replaceAll(replaceReg,emptyStr))<=0){
    		if(array[0].toLowerCase().replaceAll(replaceReg,emptyStr).startsWith(val))
    			return 0;
    		else
    			return -1;
    	}else if(val.compareTo(array[iLen-1].toLowerCase().replace(" ",emptyStr).replace("-",emptyStr))>=0){
    		return iLen-1;
    	}
		//System.out.println(array[0]+":"+array[array.length-1]);
		//System.out.println(array[0]+":"+val.compareTo(array[0].toLowerCase().replaceAll(replaceReg,emptyStr)));
		//System.out.println(array[0]+":"+val);
		//System.out.println(array[0]+":"+array[0].toLowerCase().replaceAll("[: . , - ]",emptyStr));


    	int counter=0;
    	int subStrLen1,subStrLen0,cprRes1,cprRes0,cprRes;String houXuan1,houXuan0;
    	while(low<high){
    		counter+=1;
    		//System.out.println("bfc_1_debug  "+low+":"+high+"   执行第"+counter+" 次");
    		middle = (low+high)/2;
    		houXuan1 = array[middle+1].toLowerCase().replaceAll(replaceReg,emptyStr);
    		houXuan0 = array[middle  ].toLowerCase().replaceAll(replaceReg,emptyStr);
    		cprRes1=houXuan1.compareTo(val);
        	cprRes0=houXuan0.compareTo(val);
        	if(cprRes0>=0){
        		high=middle;
        	}else if(cprRes1<=0){
        		//System.out.println("cprRes1<=0 && cprRes0<0");
        		//System.out.println(houXuan1);
        		//System.out.println(houXuan0);
        		low=middle+1;
        	}else{
        		//System.out.println("asd");
        		high=middle;
        	}
    	}
    	
    	int resPreFinal;
    	if(low==high) resPreFinal = high;
    	else{
    		resPreFinal = Math.abs(array[low].toLowerCase().replaceAll(replaceReg,emptyStr).compareTo(val))>Math.abs(array[high].toLowerCase().replaceAll(replaceReg,emptyStr).compareTo(val))?high:low;
    	}
		//System.out.println(resPreFinal);
		//System.out.println("执行了几次："+counter);
    	if(isLoose) return resPreFinal;
    	houXuan1 = array[resPreFinal].toLowerCase().replaceAll(replaceReg,emptyStr);
    	//show("houXuan1"+houXuan1);
    	if(val.length()>houXuan1.length())
    		return -1;//判为矢匹配.
    	else{
    		if(houXuan1.substring(0,val.length()).compareTo(val)!=0)
    			return -1;//判为矢匹配.
    		else return resPreFinal;//
    	}
    }
    
    //binary_find_closest: with_charset! with_charset! with_charset!
    public int  binary_find_closest2(String[] array,String val,boolean isLoose) throws UnsupportedEncodingException{
    	int middle = 0;
    	int iLen = array.length;
    	int low=0,high=iLen-1;
    	if(array==null || iLen<1){
    		return -1;
    	}
    	//if(iLen==1)
    	//	return 0;
    	
    	byte[] valBA = val.getBytes(_encoding);
    	
    	if(compareByteArray(valBA,array[0].toLowerCase().replaceAll(replaceReg,emptyStr).getBytes(_encoding))<=0){
    		if(array[0].toLowerCase().replaceAll(replaceReg,emptyStr).startsWith(val))
    			return 0;
    		else
    			return -1;
    	}else if(compareByteArray(valBA,array[iLen-1].toLowerCase().replaceAll(replaceReg,emptyStr).getBytes(_encoding))>=0){
    		//System.out.println("执行了几asdas次：");
    		return -1;
    	}
    	int counter=0;
    	int subStrLen1,subStrLen0,cprRes1,cprRes0,cprRes;
    	byte[] houXuan1BA,houXuan0BA;
    	while(low<high){
    		counter+=1;
    		//System.out.println(low+":"+high);
    		middle = (low+high)/2;
    		houXuan1BA = array[middle+1].toLowerCase().replaceAll(replaceReg,emptyStr).getBytes(_encoding);
    		houXuan0BA = array[middle  ].toLowerCase().replaceAll(replaceReg,emptyStr).getBytes(_encoding);
    		cprRes1=compareByteArray(houXuan1BA,valBA);
        	cprRes0=compareByteArray(houXuan0BA,valBA);
        	if(cprRes0>=0){
        		high=middle;
        	}else if(cprRes1<=0){
        		//System.out.println("cprRes1<=0 && cprRes0<0");
        		//System.out.println(houXuan1);
        		//System.out.println(houXuan0);
        		low=middle+1;
        	}else{
        		//System.out.println("asd");
        		high=middle;
        	}
    	}
    	
    	int resPreFinal;
    	if(low==high) resPreFinal = high;
    	else{
    		resPreFinal = Math.abs(compareByteArray(array[low].toLowerCase().replaceAll(replaceReg,emptyStr).getBytes(_encoding),valBA))>Math.abs(compareByteArray(array[high].toLowerCase().replaceAll(replaceReg,emptyStr).getBytes(_encoding),valBA))?high:low;
    	}
		//System.out.println(resPreFinal);
		//System.out.println("执行了几次："+counter);
    	if(isLoose) return resPreFinal;
    	String houXuan1 = array[resPreFinal].toLowerCase().replaceAll(replaceReg,emptyStr);
    	//show("houXuan1"+houXuan1);
    	if(val.length()>houXuan1.length())
    		return -1;//判为矢匹配.
    	else{
    		if(houXuan1.substring(0,val.length()).compareTo(val)!=0)
    			return -1;//判为矢匹配.
    		else return resPreFinal;//
    	}
    }
   
    public static int  binary_find_closest_loose(String[] array,String val){
    	int middle = 0;
    	int iLen = array.length;
    	int low=0,high=iLen-1;
    	if(array==null || iLen<1){
    		return -1;
    	}
    	//if(iLen==1)
    	//	return 0;
    	
    	if(val.compareTo(array[0])<=0){
    		if(array[0].startsWith(val))
    			return 0;
    		else
    			return -1;
    	}else if(val.compareTo(array[iLen-1])>=0){
    		return iLen-1;
    	}
		//System.out.println(array[0]+":"+array[array.length-1]);
		//System.out.println(array[0]+":"+val.compareTo(array[0].toLowerCase().replaceAll(replaceReg,emptyStr)));
		//System.out.println(array[0]+":"+val);
		//System.out.println(array[0]+":"+array[0].toLowerCase().replaceAll("[: . , - ]",emptyStr));


    	int counter=0;
    	int subStrLen1,subStrLen0,cprRes1,cprRes0,cprRes;String houXuan1,houXuan0;
    	while(low<high){
    		counter+=1;
    		//System.out.println("bsl_debug"+low+":"+high);
    		middle = (low+high)/2;
    		houXuan1 = array[middle+1];
    		houXuan0 = array[middle  ];
    		cprRes1=houXuan1.compareTo(val);
        	cprRes0=houXuan0.compareTo(val);
        	if(cprRes0>=0){
        		high=middle;
        	}else if(cprRes1<=0){
        		//System.out.println("cprRes1<=0 && cprRes0<0");
        		//System.out.println(houXuan1);
        		//System.out.println(houXuan0);
        		low=middle+1;
        	}else{
        		//System.out.println("asd");
        		high=middle;
        	}
    	}
    	
    	int resPreFinal;
    	if(low==high) resPreFinal = high;
    	else{
    		resPreFinal = Math.abs(array[low].compareTo(val))>Math.abs(array[high].compareTo(val))?high:low;
    	}
		//System.out.println(resPreFinal);
		//System.out.println("执行了几次："+counter);
    	houXuan1 = array[resPreFinal];
    	//show("houXuan1"+houXuan1);
    	return resPreFinal;//
    }
   
    //per-byte comparing byte array
    static int compareByteArray(byte[] A,byte[] B){
    	int la = A.length,lb = B.length;
    	for(int i=0;i<Math.min(la, lb);i++){
    		int cpr = (int)(A[i]&0xff)-(int)(B[i]&0xff);
    		if(cpr==0)
    			continue;
    		return cpr;
    	}
    	if(la==lb)
    		return 0;
    	else return la>lb?1:-1;
    }
    
	private  long _read_number(ByteBuffer sf) {
    	if(_number_width==4)
    		return sf.getInt();
    	else
    		return sf.getLong();
	}
	private  long _read_number(DataInputStream  sf) {
    	if(_number_width==4)
			try {
				return sf.readInt();
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		else
			try {
				return sf.readLong();
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
	}

    //解压 utils
    public static byte[] zlib_decompress(byte[] encdata,int offset) {
	    try {
			    ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			    InflaterOutputStream inf = new InflaterOutputStream(out); 
			    inf.write(encdata,offset, encdata.length-offset); 
			    inf.close(); 
			    return out.toByteArray(); 
		    } catch (Exception ex) {
		    	ex.printStackTrace(); 
		    	return "ERR".getBytes(); 
		    }
    }
    public static byte[] zlib_decompress(byte[] encdata,int offset,int size) {
	    try {
			    ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			    InflaterOutputStream inf = new InflaterOutputStream(out); 
			    inf.write(encdata,offset, size); 
			    inf.close(); 
			    return out.toByteArray(); 
		    } catch (Exception ex) {
		    	ex.printStackTrace(); 
		    	return "ERR".getBytes(); 
		    }
    }    
    
    public static byte[] zlib_decompress2(byte[] data,int offset) {  
        byte[] output = new byte[0];  
  
        Inflater decompresser = new Inflater();  
        decompresser.reset();  
        decompresser.setInput(data,offset,data.length-offset);  

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);  
        try {  
            byte[] buf = new byte[1024];  
            while (!decompresser.finished()) {  
                int i = decompresser.inflate(buf);  
                o.write(buf, 0, i);  
            }  
            output = o.toByteArray();  
        } catch (Exception e) {  
            output = data;  
            e.printStackTrace();  
        } finally {  
            try {  
                o.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        decompresser.end();  
        return output;  
    }  
    public static byte[] gzip_decompress(byte[] bytes,int offset) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(bytes,offset, bytes.length-offset);  
        try {  
            GZIPInputStream ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();
        }  
  
        return out.toByteArray();  
    }  
    
	private static long calcChecksum2(byte[] bytes) {
        Adler32 a32 = new Adler32();
        a32.update(bytes);
        return a32.getValue();
    }
    public static short getShort(byte buf1, byte buf2) 
    {
        short r = 0;
        r |= (buf1 & 0x00ff);
        r <<= 8;
        r |= (buf2 & 0x00ff);
        return r;
    }
    
    public static int getInt(byte buf1, byte buf2, byte buf3, byte buf4) 
    {
        int r = 0;
        r |= (buf1 & 0x000000ff);
        r <<= 8;
        r |= (buf2 & 0x000000ff);
        r <<= 8;
        r |= (buf3 & 0x000000ff);
        r <<= 8;
        r |= (buf4 & 0x000000ff);
        return r;
    }
   
    public static String byteTo16(byte bt){
        String[] strHex={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        String resStr="";
        int low =(bt & 15);
        int high = bt>>4 & 15;
        resStr = strHex[high]+strHex[low];
        return resStr;
    }

    
    //per-byte byte array comparing
    private final static boolean compareByteArrayIsPara(byte[] A,byte[] B){
    	for(int i=0;i<A.length;i++){
    		if(A[i]!=B[i])
    			return false;
    	}
    	return true;
    }
    static int indexOf(byte[] source, int sourceOffset, int sourceCount, byte[] target, int targetOffset, int targetCount, int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        byte first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first)
                    ;
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++)
                    ;

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }
    public static long getLong(byte[] buf) 
    {
        long r = 0;
        r |= (buf[0] & 0xff);
        r <<= 8;
        r |= (buf[1] & 0xff);
        r <<= 8;
        r |= (buf[2] & 0xff);
        r <<= 8;
        r |= (buf[3] & 0xff);
        r <<= 8;
        r |= (buf[4] & 0xff);
        r <<= 8;
        r |= (buf[5] & 0xff);
        r <<= 8;
        r |= (buf[6] & 0xff);
        r <<= 8;
        r |= (buf[7] & 0xff);
        return r;
    }
	
    public void showToast(String text)
    {
        //if(m_currentToast != null)
        //    m_currentToast.cancel();
        m_currentToast = Toast.makeText(a, text, Toast.LENGTH_SHORT);
        m_currentToast.show();

    }Toast m_currentToast;
    void showT(String text)
    {
        if(m_currentToast != null)
        {
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            m_currentToast.cancel();
        }
        m_currentToast = Toast.makeText(a, text, Toast.LENGTH_SHORT);
        m_currentToast.show();

    }     
    
    final static byte[] _zero4 = new byte[]{0,0,0,0};
    final static byte[] _1zero3 = new byte[]{1,0,0,0};
    final static byte[] _2zero3 = new byte[]{2,0,0,0};

    public static long toInt(byte[] buffer,int offset) {   
        int  values = 0;   
        for (int i = 0; i < 4; i++) {    
            values <<= 8; values|= (buffer[offset+i] & 0xff);   
        }   
        return values;  
     }     
    public static long toLong(byte[] buffer,int offset) {   
        long  values = 0;   
        for (int i = 0; i < 8; i++) {    
            values <<= 8; values|= (buffer[offset+i] & 0xff);   
        }   
        return values;  
     } 
    public static char toChar(byte[] buffer,int offset) {   
        char  values = 0;   
        for (int i = 0; i < 2; i++) {    
            values <<= 8; values|= (buffer[offset+i] & 0xff);   
        }   
        return values;  
     } 
    
    static void show(String val){
    	System.out.println(val);
    }
    private final static String replaceReg = " |:|\\.|,|-|\'";
    private final static String emptyStr = "";
}