package file_conversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class cesna_conv {
	
	public void convert_feat_file(String file_name) throws IOException
	{
		String line="";
		BufferedReader br=new BufferedReader(new FileReader(file_name));
		BufferedWriter bw=new BufferedWriter(new FileWriter(file_name+"_n"));
		int node_cnt=1;
		HashMap<Integer,ArrayList<String>> feat_node_map=new HashMap<Integer,ArrayList<String>>();
		boolean map_ini_flag=false;
		int max_feat_num=0;
		while((line=br.readLine())!=null)
		{
			String[] line_data=line.split(" ");
			if(!map_ini_flag)
			{
				ini_map(feat_node_map,line_data.length-1);
				max_feat_num=line_data.length-1;
				map_ini_flag=true;
			}
			int node_num=Integer.parseInt(line_data[0].toString());
			int feat_num=0;
			for(int i=1;i<line_data.length;i++)
			{
				if(Integer.parseInt(line_data[i])==1)
				{
					
					feat_node_map.get(feat_num).add(Integer.toString(node_num));
					//bw.write(node_num+"\t"+feat_num+"\n");
				}
				feat_num++;
			}
			node_cnt++;
		}
		
		for(int feat_num=0;feat_num<max_feat_num;feat_num++)
		{
			ArrayList<String> curr=feat_node_map.get(feat_num);
			for(String tmp: curr)
			{
				bw.write(tmp+"\t"+feat_num+"\n");
			}
		}
		
		br.close();
		bw.close();
		
		File old_file=new File(file_name);
		File renamed_old_file=new File(file_name+"_old");
		old_file.renameTo(renamed_old_file);
		
		File new_file=new File(file_name+"_n");
		File renamed_new_file=new File(file_name);
		new_file.renameTo(renamed_new_file);
	}
	
	public void ini_map(HashMap<Integer,ArrayList<String>> feat_node_map,int feat_num)
	{
		for(int i=0;i<feat_num;i++)
		{
			feat_node_map.put(i, new ArrayList<String>());
		}
	}
	
	public void conv_circle_file(String file_name) throws IOException
	{
		String line="";
		BufferedReader br=new BufferedReader(new FileReader(file_name));
		BufferedWriter bw=new BufferedWriter(new FileWriter(file_name+"_n"));
		while((line=br.readLine())!=null)
		{
			String[] part=line.split("\t");;
			if(part.length>2)
			{
				bw.write(merge_array(part)+"\n");
			}
		}
		
		br.close();
		bw.close();
		
		File old_file=new File(file_name);
		File renamed_old_file=new File(file_name+"_old");
		old_file.renameTo(renamed_old_file);
		
		File new_file=new File(file_name+"_n");
		File renamed_new_file=new File(file_name);
		new_file.renameTo(renamed_new_file);
		
	}
	
	public String merge_array(String[] part)
	{
		String temp="";
		for(int i=1;i<part.length;i++)
		{
			temp+=part[i]+" ";
		}
		return temp.substring(0,temp.length()-1);
	}
	
	public static void main(String[] args)
	{
		cesna_conv o=new cesna_conv();
		/*try {
			o.convert_feat_file("C:\\cygwin\\home\\cse\\CAA\\snap-master\\examples\\cesna\\0.nodefeat");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		try {
			o.conv_circle_file("C:\\cygwin\\home\\cse\\CAA\\datasets\\facebook\\0.circles");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
