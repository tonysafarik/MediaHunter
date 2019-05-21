import * as React from "react";
import axios from "axios";
import { RequestState } from "./content/template/header/requests/Request";
import { RouteState } from "../App";
import {getHeapSnapshot} from "v8";

const apiClient = axios.create({
  baseURL: "http://localhost:4040",
  headers: (localStorage.getItem("token") !== null? {Authorization: { toString() {return `Basic ${localStorage.getItem('token')}`} }} : {})
});

const BackendApi = {
  login: {
    attempt(username: string, password: string) {
      const headers = {headers: {"Authorization": "Basic " + btoa(username + ":" + password)}};
      console.log(headers);
      return apiClient.get("/login", headers);
    }
  },
  channel: {
    getPreviewsByExternalId(externalId: string) {
      return apiClient.get("/channel/search/" + externalId);
    },
    getById(id: string) {
      return apiClient.get("/channel/" + id);
    },
    register(externalId: string, mcpName: string, trusted: boolean) {
      const obj = { externalId, mcpName, trusted };
      let promise = apiClient.post("/channel", obj);
      console.log(promise);
      return promise;
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
  markDone: () => {}
};

export const AppContext = React.createContext(defaultRequestStorage);

export interface RequestStorage {
  requests: RequestState[];
  addRequest: (request: RequestState) => void;
  markDone: (request: RequestState) => void;
}

export default BackendApi;
