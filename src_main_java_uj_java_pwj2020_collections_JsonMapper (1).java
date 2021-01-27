package uj.java.pwj2020.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




public interface JsonMapper {

    String toJson(Map<String, ?> map);
    static JsonMapper defaultInstance() {
        return new ToJsonConverter();
    }

}
class ToJsonConverter implements  JsonMapper
{
    private String ConvertPart (Object object)
    {
        if (object == null)
            return "null";
        if(object instanceof Number)
        {
            return String.valueOf(object);
        }
        if (object instanceof String)
        {
            return ('"' + ((String) object).replace("\"","\\\"")+ '"');
        }
        if (object instanceof Boolean)
        {
            return String.valueOf(object);
        }
        if (object instanceof  Map)
        {
            return toJson((Map)object);
        }
        if (object instanceof List)
        {
            String result = "[";
            for (Object x : (List)object)
                result += ConvertPart(x) + ",";
            if (result.charAt(result.length() - 1 ) == ',')
                result = result.substring(0,result.length() - 1);
            result += "]";
            return  result;
        }
        return null;
    }
    public String toJson(Map<String, ?> map)
    {
        if (map == null)
            return "{}";
        String result = "{";
        List<String> keyList = new ArrayList<>( map.keySet());
        for (int i =0; i < keyList.size(); i++)
        {
            result += '"' + keyList.get(i)  + '"' +":" +  ConvertPart(map.get(keyList.get(i))) + ",";
        }
        if (result.charAt(result.length() - 1 ) == ',')
            result = result.substring(0,result.length() - 1);
        result += '}';
        return result;
    }
}