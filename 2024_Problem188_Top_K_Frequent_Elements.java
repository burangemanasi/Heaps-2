//347. Top K Frequent Elements - https://leetcode.com/problems/top-k-frequent-elements/

//using HashMap and Heap
//Time Complexity: O(n*log(k))
//Space Complexity: O(n)

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //create frequency map
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0)+1);
        }
        //to get top k elements  create and add to pq
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> map.get(a) - map.get(b)); //min heap
        for(int num : map.keySet()){
            pq.add(num);
            if(pq.size() > k){
                pq.poll(); //at the end, 'k' elements remain in pq
            }
        }
        int[] result = new int[k];
        int idx=0;
        while(!pq.isEmpty()){
            result[idx] = pq.poll();
            idx++;
        }
        return result;
    }
}


//Bucket sort
//Time Complexity: O(n)
//Space Complexity: O(n)
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //create frequency map
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0)+1);
        }
        //reverse mapping
        HashMap<Integer, List<Integer>> freqToNumMap = new HashMap<>();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        //add elements in reverse map
        for(int num : map.keySet()){
            int freq = map.get(num);
            freqToNumMap.putIfAbsent(freq, new ArrayList<>());
            freqToNumMap.get(freq).add(num);
            //update max,min
            min = Math.min(min, freq);
            max = Math.max(max, freq);
        }

        int[] result = new int[k];
        int idx=0;
        for(int i=max; i>=min && idx<k; i--){
            //skip all other numbers between min and max if they don't exist in freqToNumMap
            if(!freqToNumMap.containsKey(i)) continue;
            //get the list of elements for the given freq
            List<Integer> currList = freqToNumMap.get(i);
            for(int j=0; j<currList.size() && idx<k; j++){
                result[idx] = currList.get(j);
                idx++;
            }
        }
        return result;
    }
}