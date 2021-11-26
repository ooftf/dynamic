package com.ooftf.demo.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class DXEngine {
    Context context;
    Gson gson = new Gson();
    JSONObject structure;
    JSONObject data;
    Model model = new Model();
    View process(JSONObject jsonObject) {
        try {
            JSONObject container = jsonObject.getJSONObject("container");
            JSONArray containerData = container.getJSONArray("data");
            ArrayList<ContainerModel> cs = gson.fromJson(containerData.toString(), new TypeToken<ArrayList<ContainerModel>>() {
            }.getType());
            for (ContainerModel cm : cs) {
                model.containerModels.put(cm.id + cm.version, cm);
            }

/*            for (int i = 0; i < data.length(); i++) {
                ContainerModel containerModel = new ContainerModel();
                JSONObject containerJson = data.getJSONObject(i);
                if(containerJson.has("id")){
                    containerModel.id = containerJson.getString("id");
                }
                if(containerJson.has("type")){
                    containerModel.type = containerJson.getString("type");
                }
                if(containerJson.has("version")){
                    containerModel.version = containerJson.getString("version");
                }
                if(containerJson.has("fileId")){
                    containerModel.fileId = containerJson.getString("fileId");
                }
                if(containerJson.has("md5")){
                    containerModel.md5 = containerJson.getString("md5");

                }
                if(containerJson.has("bizCode")){
                    containerModel.bizCode = containerJson.getString("bizCode");

                }
                if(containerJson.has("ext")){
                    containerModel.ext = containerJson.getJSONObject("ext");
                }
            }*/
            data = jsonObject.getJSONObject("data");
            JSONObject hierarchy = jsonObject.getJSONObject("hierarchy");
            String rootValue = hierarchy.getString("root");
            structure = hierarchy.getJSONObject("structure");


            DataModel dataModel = findDataModelByName(rootValue);
            ContainerModel containerModel = findContainerModelByDataModel(dataModel);

            handle(rootValue,dataModel,containerModel);

            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    DataModel findDataModelByName(String name) throws JSONException {
        JSONObject dataItem = data.getJSONObject(name);
        return gson.fromJson(dataItem.toString(), DataModel.class);
    }

    ContainerModel findContainerModelByDataModel(DataModel dataModel){
        if(dataModel.layout == null){
            return model.containerModels.get(dataModel.templateId + dataModel.version);
        }
        return null;
    }
    private View handle(String rootValue,DataModel dataModel , ContainerModel containerModel) throws JSONException {
        if (dataModel.layout != null) {// layout 布局
            View result = null;
            if (dataModel.layout.type.equals("root")) {
                RootView view = new RootView(context);
                JSONArray children = structure.getJSONArray(rootValue);
                for (int i = 0; i < children.length(); i++) {
                    String childValue = children.getString(i);
                    DataModel childDataModel = findDataModelByName(childValue);
                    ContainerModel childContainerModel = findContainerModelByDataModel(childDataModel);
                    if(childDataModel.position.equals("header")){
                        view.addHeader(handle(childValue,childDataModel,childContainerModel));
                    }else if(childDataModel.position.equals("sticky")){
                        view.addSticky(handle(childValue,childDataModel,childContainerModel));
                    }else if(childDataModel.position.equals("body")){
                        view.addBody(handle(childValue,childDataModel,childContainerModel));
                    }
                }
            } else if (dataModel.layout.type.equals("linear")) {
                LinearLayout linearLayout = new LinearLayout(context);
                JSONArray children = structure.getJSONArray(rootValue);
                for (int i = 0; i < children.length(); i++) {
                    String childValue = children.getString(i);
                    DataModel childDataModel = findDataModelByName(childValue);
                    ContainerModel childContainerModel = findContainerModelByDataModel(childDataModel);
                    linearLayout.addView(handle(childValue,childDataModel,childContainerModel ));
                }
            } else if (dataModel.layout.type.equals("waterfall")) {
                RecyclerView view = new RecyclerView(context);
                view.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

                JSONArray children = structure.getJSONArray(rootValue);

            }else if(dataModel.layout.type.equals("tabLayout")){

            }else if(dataModel.layout.type.equals("tabContent")){
                ViewPager2 viewPager2 = new ViewPager2(context);
                JSONArray children = structure.getJSONArray(rootValue);
                viewPager2.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return new RecyclerView.ViewHolder(CSService.createView(context, containerModel.bizCode, 0)) {
                        };
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                        String childValue = null;
                        try {
                            childValue = children.getString(position);
                            DataModel childDataModel = findDataModelByName(childValue);
                            ContainerModel childContainerModel = findContainerModelByDataModel(childDataModel);
                            CSService.CSCardInstance csCardInstance = CSService.process(dataModel.field, containerModel.ext);
                            CSService.bindView(context, holder.itemView, "", csCardInstance);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public int getItemCount() {
                        return children.length();
                    }

                    @Override
                    public int getItemViewType(int position) {
                        return position;
                    }
                });
            }


            return result;
        } else {

            View view = CSService.createView(context, containerModel.bizCode, 0);
            CSService.CSCardInstance csCardInstance = CSService.process(dataModel.field, containerModel.ext);
            CSService.bindView(context, view, "", csCardInstance);
            return view;
        }


    }
}
