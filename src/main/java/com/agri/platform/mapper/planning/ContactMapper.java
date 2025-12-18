package com.agri.platform.mapper.planning;

import com.agri.platform.entity.planning.Contact;
import org.apache.ibatis.annotations.Mapper;

@Mapper  // 必须加这个注解，让Spring识别为Mapper接口
public interface ContactMapper {
    // 1. 插入数据（对应XML中id="insert"的SQL）
    int insertContact(Contact contact);  // 返回值int：影响的行数（成功插入返回1）

    // 2. 根据ID查询（对应XML中id="selectById"的SQL）
    Contact selectContactById(Long id);  // 参数：主键ID；返回值：查询到的Contact对象

    // 3. 根据ID删除（对应XML中id="deleteById"的SQL）
    int deleteContactById(Long id);  // 返回值int：影响的行数（成功删除返回1）

    // （可选）添加更多自定义方法，比如根据姓名查询、查询所有数据等
    // Contact selectContactByName(String name);
    // List<Contact> selectAllContacts();
}