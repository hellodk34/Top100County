import com.alibaba.fastjson.JSON;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hellodk
 * @date 2021-01-19 22:36
 */
public class Top100County {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // in your computer, use your own absolute path for file `top100.json`
        String path = "/Users/huadekai/Downloads/personal-projects/TestJava/resources/top100.json";
        String jsonContent = FileUtil.readFile(path);
        List<County> counties = JSON.parseArray(jsonContent, County.class);
        Map<String, Integer> map = new HashMap<>();
        for (County item : counties) {
            String province = item.getProvince();
            if (map.containsKey(province)) {
                Integer value = map.get(province);
                map.put(province, ++value);
            }
            else {
                map.put(province, 1);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        // 降序排序
        list.sort((o1, o2) -> o2.getValue() - o1.getValue());
        System.out.println("2020---全国百强县---各省都有多少个");
        System.out.println();
        for (Map.Entry<String, Integer> entry : list) {
            // 该省份有多少个百强县
            int count = entry.getValue();
            String currentProvince = entry.getKey();
            String displayCounty = mostProsperousCounty(currentProvince, counties);
            if (count > 1) {
                System.out.println(currentProvince + ": " + count + "个, 经济最发达的县（市、旗）是: " + displayCounty);
            }
            else {
                System.out.println(currentProvince + ": " + count + "个, 这个县（市、旗）是: " + displayCounty);
            }
        }

    }

    /**
     * 计算 该省份 经济最发达的县 市 旗
     */
    private static String mostProsperousCounty(String currentProvince, List<County> counties) {
        String mostProsperousCounty = "";
        for (County item : counties) {
            String province = item.getProvince();
            if (currentProvince.equalsIgnoreCase(province)) {
                mostProsperousCounty = item.getCounty();
                break;
            }
        }
        return mostProsperousCounty;
    }
}
