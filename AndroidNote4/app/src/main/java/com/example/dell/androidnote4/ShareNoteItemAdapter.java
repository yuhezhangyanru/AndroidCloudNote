package com.example.dell.androidnote4;
        import android.annotation.SuppressLint;
        import android.os.Message;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.TextView;

        import java.util.List;

/**
 * Created by DELL on 2018/5/26.
 */

public class ShareNoteItemAdapter extends BaseAdapter {

    private List<NoteItemInfo> mData;//定义数据。
    private LayoutInflater mInflater;//定义Inflater,加载我们自定义的布局。
    public TextView txtTitle;
    public TextView txtContent;
    public TextView txtModifyTime;
    public TextView txtShareName;//谁分享了笔记
    public Button btnDelete;
    public Button btnModify;

    /*
    定义构造器，在Activity创建对象Adapter的时候将数据data和Inflater传入自定义的Adapter中进行处理。
    */
    public ShareNoteItemAdapter(LayoutInflater inflater, List<NoteItemInfo> data) {
        mInflater = inflater;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("WrongConstant")
    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        //获得ListView中的view
        final View noteItemView = mInflater.inflate(R.layout.item_simpleadapter, null);
        //获得学生对象
        final NoteItemInfo noteInfo = mData.get(position);
        //获得自定义布局中每一个控件的对象。
        txtTitle = (TextView) noteItemView.findViewById(R.id.txtTitle);
        txtContent = (TextView) noteItemView.findViewById(R.id.txtContent);
        txtModifyTime = (TextView) noteItemView.findViewById(R.id.txtModifyTime2);
        btnDelete = (Button) noteItemView.findViewById(R.id.btnDelete);
        btnModify = (Button) noteItemView.findViewById(R.id.btnModify);
        txtShareName = (TextView)noteItemView.findViewById(R.id.txtfromUser);

        //将数据一一添加到自定义的布局中。
        txtTitle.setText(noteInfo.title);
        String content  = noteInfo.content;
        if(content.length()>200)
        {
            content = content.substring(200);
        }
        txtContent.setText(content);//.content);
        if(noteInfo.noteType == NoteItemInfo.NoteType.MyNote) //我的笔记
        {
            txtShareName.setText("");
            if(noteInfo.docID<0)
            {
                txtModifyTime.setText("");
            }
            else {
                txtModifyTime.setText(noteInfo.modify_time + "#" + noteInfo.tag);
            }

            btnDelete.setText("删除");
            btnModify.setText("修改");
            btnDelete.setVisibility(View.INVISIBLE);
            btnModify.setVisibility(View.INVISIBLE);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LogTool.prnit(this,"点击删除按钮");
                    Message message= Message.obtain();
                    message.obj="跳转到删除";
                    message.arg1 = noteInfo.docID;
                    GlobalData.handlerNoteList.sendMessage(message);
                }
            });
            btnModify.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LogTool.prnit(this,"点击修改按钮");
                    Message message = Message.obtain();
                    message.obj = "跳转到修改";
                    message.arg1 = noteInfo.docID;
                    GlobalData.handlerNoteList.sendMessage(message);
                }
            });
        }
        else{  //别人或者我分享出去的笔记
            txtShareName.setText("来自 "+noteInfo.username+" 的分享");
            if(noteInfo.docID<0)
            {
                txtModifyTime.setText("");
            }
            else {
                txtModifyTime.setText(noteInfo.modify_time + "#" + noteInfo.tag);
            }

            btnDelete.setText("移动到我的笔记");
            btnModify.setText("不看该分享");
            btnDelete.setVisibility(View.INVISIBLE);
            btnModify.setVisibility(View.INVISIBLE);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO 跳转到添加到本地
                    LogTool.prnit(this,"跳转到添加到本地");
                    Message message= Message.obtain();
                    message.obj="跳转到添加到本地";
                    message.arg1 = noteInfo.docID;
                    GlobalData.handlerNoteList.sendMessage(message);
                }
            });
            btnModify.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO 跳转到删除本地分享
                    LogTool.prnit(this,"跳转到删除本地分享");
                    Message message = Message.obtain();
                    message.obj = "跳转到删除本地分享";
                    message.arg1 = noteInfo.docID;
                    GlobalData.handlerNoteList.sendMessage(message);
                }
            });
        }

        return noteItemView;
    }
}