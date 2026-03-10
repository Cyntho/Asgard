import {useQuery} from "@tanstack/react-query";
import {useAuthStore} from "@stores/auth.store.jsx";
import {Form, Link, useNavigate, useSubmit} from "react-router-dom";
import PageTitle from "@layout/PageTitle.jsx";
import {AlertCircle, Loader2} from "lucide-react";
import React from "react";

import {useRef, useState } from "react";
import {toast} from "react-toastify";

export function TeamspeakServerConfig({config}) {

  const labelStyle = "block text-lg font-semibold text-primary dark:text-light mb-2";
  const textFieldStyle = "w-full px-4 py-2 text-base border rounded-md transition border-primary dark:border-light focus:ring focus:ring-dark dark:focus:ring-lighter focus:outline-none text-gray-800 dark:text-lighter bg-white dark:bg-gray-600 placeholder-gray-400 dark:placeholder-gray-300";

  const formRef = useRef(null);
  const configRef = useRef(config);

  const [displayName, setDisplayName] = React.useState(config?.displayName);
  const [host, setHost] = React.useState(config?.host);
  const [webQueryPort, setWebQueryPort] = React.useState(config?.webQueryPort);
  const [sshPort, setSshPort] = React.useState(config?.sshPort);


  const submit = useSubmit();


  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(e);
    const formData = formRef.current;
    submit(formData, {method: "POST"}).then(r => toast.info("Successfully Submitted!"));
  }

  return (
    <div className="min-h-screen flex items-center justify-center font-primary dark:bg-darkbg px-4 py-8">
      <div className="w-full max-w-3xl bg-white dark:bg-gray-800 shadow-xl rounded-2xl border border-gray-200 dark:border-gray-700 overflow-hidden">
        {/* Header */}
        <div className="px-8 py-6 border-b border-gray-200 dark:border-gray-700 bg-gradient-to-r from-primary/10 to-primary/5 dark:from-gray-800 dark:to-gray-900">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white">
            Teamspeak Server Einstellungen
          </h2>
          <p className="mt-1 text-sm text-gray-600 dark:text-gray-300">
            Bearbeite hier die Verbindungseinstellungen deines Teamspeak Servers.
          </p>
        </div>

        {/* Body */}
        <div className="px-8 py-6 space-y-8">
          {/* Allgemein */}
          <section>
            <h3 className="text-sm font-semibold text-gray-500 dark:text-gray-400 uppercase tracking-wide mb-4">
              Allgemein
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  Anzeigename
                </label>
                <input
                  type="text"
                  name="displayName"
                  defaultValue={config.displayName}
                  placeholder="Name deines Servers"
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  Host
                </label>
                <input
                  type="text"
                  name="host"
                  defaultValue={config.host}
                  placeholder="z.B. localhost oder ts.example.com"
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>

              <div className="flex items-center gap-3 mt-1">
                <label className="text-sm font-medium text-gray-700 dark:text-gray-200">
                  HTTPS verwenden
                </label>
                <button
                  type="button"
                  className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                    config.useHttps
                      ? "bg-primary"
                      : "bg-gray-300 dark:bg-gray-600"
                  }`}
                >
                  <span
                    className={`inline-block h-5 w-5 transform rounded-full bg-white shadow transition-transform ${
                      config.useHttps ? "translate-x-5" : "translate-x-1"
                    }`}
                  />
                </button>
              </div>

              <div className="flex items-center gap-3 mt-1">
                <label className="text-sm font-medium text-gray-700 dark:text-gray-200">
                  Server aktiv
                </label>
                <button
                  type="button"
                  className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                    config.enabled
                      ? "bg-emerald-500"
                      : "bg-gray-300 dark:bg-gray-600"
                  }`}
                >
                  <span
                    className={`inline-block h-5 w-5 transform rounded-full bg-white shadow transition-transform ${
                      config.enabled ? "translate-x-5" : "translate-x-1"
                    }`}
                  />
                </button>
              </div>
            </div>
          </section>

          {/* Ports */}
          <section>
            <h3 className="text-sm font-semibold text-gray-500 dark:text-gray-400 uppercase tracking-wide mb-4">
              Ports
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  Web Query Port
                </label>
                <input
                  type="number"
                  name="webQueryPort"
                  defaultValue={config.webQueryPort}
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  SSH Port
                </label>
                <input
                  type="number"
                  name="sshPort"
                  defaultValue={config.sshPort}
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  Raw Query Port
                </label>
                <input
                  type="number"
                  name="rawPort"
                  defaultValue={config.rawPort}
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>
            </div>
          </section>

          {/* Authentifizierung */}
          <section>
            <h3 className="text-sm font-semibold text-gray-500 dark:text-gray-400 uppercase tracking-wide mb-4">
              Authentifizierung
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  Query Benutzername
                </label>
                <input
                  type="text"
                  name="queryUsername"
                  defaultValue={config.queryUsername}
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  Query Passwort
                </label>
                <input
                  type="password"
                  name="queryPassword"
                  defaultValue={config.queryPassword}
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>
              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
                  API Key
                </label>
                <input
                  type="text"
                  name="apiKey"
                  defaultValue={config.apiKey}
                  className="w-full px-4 py-2 text-sm border rounded-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                />
              </div>
            </div>
          </section>

          {/* Meta-Informationen */}
          <section>
            <h3 className="text-sm font-semibold text-gray-500 dark:text-gray-400 uppercase tracking-wide mb-4">
              Metadaten
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 text-sm">
              <div>
                <span className="block text-gray-500 dark:text-gray-400">
                  Erstellt von
                </span>
                <span className="mt-1 block font-medium text-gray-800 dark:text-gray-100">
                  {config.createdByUsername}
                </span>
              </div>
              <div>
                <span className="block text-gray-500 dark:text-gray-400">
                  Zuletzt bearbeitet von
                </span>
                <span className="mt-1 block font-medium text-gray-800 dark:text-gray-100">
                  {config.updatedByUsername}
                </span>
              </div>
            </div>
          </section>
        </div>

        {/* Footer / Actions */}
        <div className="px-8 py-4 border-t border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-900 flex justify-end gap-3">
          <button
            type="button"
            className="px-4 py-2 text-sm rounded-lg border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-800 hover:bg-gray-100 dark:hover:bg-gray-700 transition"
          >
            Abbrechen
          </button>
          <button
            type="submit"
            className="px-4 py-2 text-sm rounded-lg bg-primary text-white font-medium shadow hover:bg-primary/90 transition"
          >
            Änderungen speichern
          </button>
        </div>
      </div>
    </div>
  );
}