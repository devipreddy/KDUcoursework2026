<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Serverless Counter</title>
  <style>
    * {
      box-sizing: border-box;
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen;
    }

    body {
      min-height: 100vh;
      margin: 0;
      background: linear-gradient(135deg, #667eea, #764ba2);
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .card {
      background: #ffffff;
      padding: 2.5rem 3rem;
      border-radius: 16px;
      box-shadow: 0 20px 40px rgba(0,0,0,0.15);
      text-align: center;
      width: 320px;
    }

    h1 {
      font-size: 3rem;
      margin: 0;
      color: #333;
    }

    p {
      color: #777;
      margin-bottom: 2rem;
    }

    button {
      background: #667eea;
      border: none;
      padding: 0.9rem 1.8rem;
      color: white;
      font-size: 1rem;
      border-radius: 10px;
      cursor: pointer;
      transition: transform 0.15s ease, box-shadow 0.15s ease;
      box-shadow: 0 10px 20px rgba(102,126,234,0.4);
    }

    button:hover {
      transform: translateY(-2px);
      box-shadow: 0 14px 28px rgba(102,126,234,0.5);
    }

    button:active {
      transform: translateY(0);
      box-shadow: 0 8px 16px rgba(102,126,234,0.3);
    }
  </style>
</head>

<body>
  <div class="card">
    <h1 id="count">0</h1>
    <p>Number of visits</p>
    <button onclick="increment()">Add Counter</button>
  </div>

  <script>
    const api_url = "${api_url}";

    async function load() {
      const r = await fetch(`${api_url}/counter`);
      document.getElementById("count").innerText = await r.text();
    }

    async function increment() {
      const r = await fetch(`${api_url}/counter`, { method: "PUT" });
      document.getElementById("count").innerText = await r.text();
    }

    load();
  </script>
</body>
</html>
