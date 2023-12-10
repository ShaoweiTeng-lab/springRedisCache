package com.example.redisspringboot.dto;

import lombok.Data;
import java.util.*;

@Data
public class UserInfo {
 private  String name;
 private  Integer age;
 private  List<UserInfo> friends;
}
