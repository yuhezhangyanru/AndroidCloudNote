package com.example.dell.androidnote4;

public class group_table
{
/*主键*/
        public int id;
		/*该分组下的用户ID*/
        public String user_ids;
		/*分组标题昵称，虽然不是主键，但也不可以重名*/
        public String group_name;
		/*该分组所有分享的文章ID*/
        public String note_ids;
		

}