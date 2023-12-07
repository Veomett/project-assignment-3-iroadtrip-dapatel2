import java.io.File;
import java.util.*;
public class IRoadTrip {
    ArrayList<String> mCountryList;
    Map<String, Map<String, Integer>>  mAdjustMap = new HashMap<>();
    public IRoadTrip (String [] args) {
        // Replace with your code
        if(args.length != 3){
            System.err.println("input argument is invalud");
            System.exit(1);
        }
        //read sate_name.csv
        Map<Integer, String>  nameMap = new HashMap<>();
        Scanner fScan;

        try {
            fScan = new Scanner(new File(args[2]));
            fScan.nextLine();
            while (fScan.hasNextLine()) {
                String[] row = fScan.nextLine().split("\t");
                if(row[0].equals("678")){
                    System.out.println();
                }
                if(row[4].equals("2020-12-31")){
                    nameMap.put(Integer.parseInt(row[0]), validName(row[2]));
                }
            }
            fScan.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        mCountryList = new ArrayList<>(nameMap.values());
        // read borders.txt
        try {
            fScan = new Scanner(new File(args[0]));
            while (fScan.hasNextLine()) {
                String[] row = fScan.nextLine().split(" = ");
                String startCountry = validName(row[0]);
                if(!mCountryList.contains(startCountry))
                    continue;
                mAdjustMap.put(startCountry, new HashMap<>());
                if(row.length == 2){
                    String[] subRow = row[1].split("; ");
                    for(String subline : subRow){
                        String[] words = subline.split(" ");
                        String connection = "";
                        for(int i = 0 ; i < words.length - 2; i++)
                            connection += words[i] + " ";
                        connection = validName(connection);
                        if(!mCountryList.contains(connection))
                            continue;
                        mAdjustMap.get(startCountry).put(connection, -1);
                    }
                }
            }
            fScan.close();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //read capdist.csv
        try {
            fScan = new Scanner(new File(args[1]));
            fScan.nextLine();
            while (fScan.hasNextLine()) {
                String[] row = fScan.nextLine().split(",");
                String firstCountry = getCountryName(row[0], nameMap);
                String secondCountry = getCountryName(row[2], nameMap);
                if(firstCountry == null || secondCountry == null){
                    continue;
                }
                if(firstCountry.isEmpty() || secondCountry.isEmpty())
                    continue;
                if(!mAdjustMap.keySet().contains(firstCountry))
                    continue;
                if(!mAdjustMap.get(firstCountry).keySet().contains(secondCountry))
                    continue;
                mAdjustMap.get(firstCountry).put(secondCountry, Integer.parseInt(row[4]));
            }
            fScan.close();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
    private String validName(String content){
        content = content.trim().split("\\(")[0].trim();
        String result = content.trim().split("/")[0].trim();
        if(result.equals("Korea, North") || result.equals("Korea, People's Republic of"))
            return "North Korea";
        if(result.equals("Korea, South") || result.equals("Korea, Republic of"))
            return "South Korea";
        if(result.equals("Congo, Democratic Republic of") || result.equals("Congo, Democratic Republic of the"))
            return "Democratic Republic of the Congo";
        if(result.equals("Republic of the Congo") || result.equals("Congo, Republic of the"))
            return "Congo";
        if(result.equals("Vietnam, Democratic Republic of"))
            return "Vietnam";
        if(result.equals("German Federal Republic"))
            return "Germany";
        if(result.equals("Macedonia"))
            return "North Macedonia";
        if(result.equals("Myanmar"))
            return "Burma";
        return result;
    }
    private String getCountryName(String strNum, Map<Integer, String>  nameMap){
        try{
            int num = Integer.parseInt(strNum);
            return nameMap.get(num);
        }catch (Exception e){
            return "";
        }
    }
    class Node {
        String name;
        int distance;
        public Node(String n, int d) {
            name = n;
            distance = d;
        }
    };
    public int getDistance (String country1, String country2) {
        // Replace with your code
        if(!mCountryList.contains(country1) || !mCountryList.contains(country2))
            return -1;
        if(!mAdjustMap.keySet().contains(country1))
            return -1;
        if(!mAdjustMap.get(country1).keySet().contains(country2))
            return -1;
        return mAdjustMap.get(country1).get(country2);
    }
    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        PriorityQueue<Node> minHeap = new PriorityQueue<>((lhs, rhs) -> lhs.distance - rhs.distance);
        minHeap.add(new Node(country1, 0));
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        Map<String, Boolean> done = new HashMap<>();
        for(String name: mCountryList){
            dist.put(name, Integer.MAX_VALUE);
            prev.put(name, "");
            done.put(name, false);
        }
        done.put(country1, true);
        dist.put(country1, 0);

        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            String country = node.name;
            if(country.equals(country2)){
                break;
            }
            if(!mAdjustMap.keySet().contains(country)){
                continue;
            }

            for (Map.Entry<String, Integer> entry : mAdjustMap.get(country).entrySet())
            {
                String key = entry.getKey();
                int dis = entry.getValue();
                if(dis == -1)
                    continue;

                if (!done.get(key) && (dist.get(country) + dis) < dist.get(key)) {
                    dist.put(key, dist.get(country) + dis);
                    minHeap.add(new Node(key, dist.get(key)));
                    prev.put(key, country);
                }
            }
            done.put(country, true);
        }

        ArrayList<String> path = new ArrayList<>();
        String con = country2;
        while(!con.isEmpty()){
            path.add(con);
            con = prev.get(con);
        }
        Collections.reverse(path);
        ArrayList<String> result = new ArrayList<>();
        if(path.size() == 0)
            return result;

        if(path.size() == 1){
            if(country1.equals(country2))
                result.add(country1 + " --> " + country2 + " (0 km.)");
            return result;
        }

        for(int i = 0 ; i < path.size() - 1 ; i++){
            String con1 = path.get(i);
            String con2 = path.get(i+1);
            result.add(con1 + " --> " + con2 + " ("+getDistance(con1, con2)+" km.)");
        }
        return result;
    }
    private String acceptCountry(Scanner scan, String order){
        String countryName = "";
        while(true){
            System.out.println("Enter the name of the "+order+" country (type EXIT to quit):");
            countryName = scan.nextLine();
            if(countryName.equals("EXIT"))
                return "";
            if(!mCountryList.contains(countryName)){
                System.out.println("Invalid country name. Please enter a valid country name. ");
            }
            break;
        }
        return countryName;
    }
    public void acceptUserInput() {
        // Replace with your code
        System.out.println("IRoadTrip - skeleton");
        Scanner scanner = new Scanner(System.in);
        while(true){
            String country1 = acceptCountry(scanner, "first");
            if(country1.isEmpty())
                break;
            String country2 = acceptCountry(scanner, "second");
            if(country2.isEmpty())
                break;
            List<String> path = findPath(country1, country2);
            System.out.println("Route from "+country1+" to "+country2+":");
            for(String item : path){
                System.out.println("* " + item);
            }
        }
    }

    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);

        a3.acceptUserInput();
    }
}