const priceValue = document.getElementById("price-value");
const quantityInput = document.getElementById("quantity-input");
const buyButton = document.getElementById("buy-button");
const sellButton = document.getElementById("sell-button");
const historyList = document.getElementById("history-list");
const chartCanvas = document.getElementById("price-chart");
const c = chartCanvas.getContext("2d");

chartCanvas.width = 800;
chartCanvas.height = 500;
const GREEN = "#2f9e44";
const RED = "#e03131";
let currentPrice = 189;
let bars = [];
priceValue.textContent = currentPrice.toFixed(2);


function drawChart() {
  c.clearRect(0, 0, chartCanvas.width, chartCanvas.height);
  bars.forEach((bar, index) => {
    c.fillStyle = bar.color;
    const barWidth = 20;
    const x = index * barWidth;

    c.fillRect( x, chartCanvas.height - bar.value,
      barWidth, bar.value );
  });

}


function updateStockPrice() {
  const newPrice = Math.floor(Math.random() * 500);
  const barColor = newPrice >= currentPrice ? GREEN : RED;
  bars.push({
    value: newPrice, color: barColor
  });

  const maxBars = Math.floor(chartCanvas.width / 20);
  if (bars.length > maxBars) {
    bars.shift();
  }
  currentPrice = newPrice;
  priceValue.textContent = currentPrice.toFixed(2);

  drawChart();

}


function addTransaction(type, qty) {

  const time = new Date().toLocaleString();
  const li = document.createElement("li");
  const contentDiv = document.createElement("div");
  contentDiv.className = "content";
  const title = document.createElement("h2");
  title.textContent = `${qty} Stocks`;
  const timestamp = document.createElement("p");
  timestamp.textContent = `${type} - ${time}`;
  const tag = document.createElement("h4");
  tag.textContent = type;
  tag.style.color = type === "Buy" ? GREEN : RED;

  contentDiv.appendChild(title);
  contentDiv.appendChild(timestamp);
  li.appendChild(contentDiv);
  li.appendChild(tag);

  historyList.prepend(li);

}


function getQuantity() {
  const quantity = Number(quantityInput.value);
  if (!quantity || quantity <= 0) {
    alert("Please enter a valid quantity!");
    return null;
  }

  return quantity;

}


buyButton.addEventListener("click", () => {
  const quantity = getQuantity();
  if (!quantity) return;
  addTransaction("Buy", quantity);
  quantityInput.value = "";

});


sellButton.addEventListener("click", () => {
  const quantity = getQuantity();
  if (!quantity) return;

  addTransaction("Sell", quantity);
  quantityInput.value = "";

});


setInterval(updateStockPrice, 5000);
