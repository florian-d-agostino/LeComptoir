const JAVA_SERVER_URL = "http://localhost:8080/receipt";

async function fetchReceipt(): Promise<void> {
  console.log(`[TypeScript] Envoi de la requête à Java sur ${JAVA_SERVER_URL}...`);

  try {
    const response = await fetch(JAVA_SERVER_URL);

    if (!response.ok) {
      throw new Error(`Erreur HTTP: ${response.status} ${response.statusText}`);
    }

    const receipt = await response.text();
    console.log("\n[TypeScript] Ticket de caisse reçu depuis Java :\n");
    console.log(receipt);
  } catch (error) {
    console.error(
      "[TypeScript] Impossible de joindre le serveur Java.",
      "Assurez-vous que l'application Java est bien démarrée (ex: mvn exec:java).",
      error
    );
  }
}

fetchReceipt();
