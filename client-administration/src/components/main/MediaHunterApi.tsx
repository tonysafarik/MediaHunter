import * as React from "react";
import axios from "axios";
import { RequestState } from "./content/template/header/requests/Request";
import { RouteState } from "../App";
import Home from "./content/Home";
import Router from "react-router-dom";
import FindChannelForm from "./content/search/channel/FindChannelForm";
import FindForm from "./content/search/FindForm";
import RecordList from "./content/search/record/RecordList";
import Login from "./content/Login";
import Channel from "./content/Channel";

const apiClient = axios.create({
  baseURL: "http://192.168.1.105:4040"
});

const BackendApi = {
  channel: {
    getChannelPreviewByExternalId(externalId: string) {
      return apiClient.get("/channel/search/" + externalId);
    },
    getChannelByExternalId(externalId: string) {
      return null;
    },
    registerChannel(externalId: string, mcpName: string, trusted: boolean) {
      const obj = { externalId, mcpName, trusted };
      return apiClient.post("/channel", obj);
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
