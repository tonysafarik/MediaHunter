import * as React from "react";
import axios from "axios";
import { RequestState } from "./content/template/header/requests/Request";
import { RouteState } from "../App";

const apiClient = axios.create({
  baseURL: "http://192.168.1.105:4040"
});

const BackendApi = {
  channel: {
    getPreviewsByExternalId(externalId: string) {
      return apiClient.get("/channel/search/" + externalId);
    },
    getById(id: string) {
      return apiClient.get("/channel/" + id);
    },
    register(externalId: string, mcpName: string, trusted: boolean) {
      const obj = { externalId, mcpName, trusted };
      return apiClient.post("/channel", obj);
    }
  },
  multimedium: {
    getPreviewsByExternalId(externalId: string) {
      return apiClient.get("/multimedium/search/" + externalId);
    },
    register(externalId: string, mcpName: string) {
      const obj = {externalId, mcpName}
      return apiClient.post("/multimedium", obj)
    },
    getById(id: string) {
      return apiClient.get("/multimedium/" + id);
    },
    getQueue() {
      return apiClient.get("/multimedium/queue");
    },
    accept(id: string) {
      return apiClient.get("multimedium/accept/" + id);
    },
    reject(id:string) {
      return apiClient.get("multimedium/reject/" + id);
    }
  }
};

export const defaultRequestStorage: RequestStorage = {
  requests: [],
  addRequest: (request: RequestState) => {},
  markDone: () => {},
  routes: []
};

export const AppContext = React.createContext(defaultRequestStorage);

export interface RequestStorage {
  requests: RequestState[];
  addRequest: (request: RequestState) => void;
  markDone: (request: RequestState) => void;
  routes: RouteState[];
}

export default BackendApi;
