const tweetBtn = document.querySelector(".tweet-btn");
const tweetInput = document.querySelector(".posts-textarea");
const postsWrapper = document.querySelector(".posts");
const tweetBox = document.querySelector(".tweet-box");

document.querySelectorAll(".posts-feed").forEach((post) => {
  const likes = post.querySelector(".likes-count");
  const retweets = post.querySelector(".retweet-count");
  const comments = post.querySelector(".comment-count");

  if (likes) likes.textContent = Math.floor(Math.random() * 100);
  if (retweets) retweets.textContent = Math.floor(Math.random() * 50);
  if (comments) comments.textContent = Math.floor(Math.random() * 20);
});


function createPost(text) {
  const post = document.createElement("div");
  post.classList.add("posts-feed");

  post.innerHTML = `
    <div class="profile-photo">
      <img src="twiiter base line images/Profile/profile-pic.png">
    </div>

    <div class="post-content">

      <div class="post-header">
        <span class="post-username">You</span>
        <span class="post-handle">@you</span>
        <span class="post-timestamp">now</span>
      </div>

      <div class="post-body">
        <p>${text}</p>
      </div>

      <div class="post-actions">

            <button class="comment-post"><img src="icons/comment.svg"><span class="comment-count">2</span></button>

            <button class="retweet-post"><img src="icons/retweet.svg"><span class="retweet-count">4</span></button>

            <button class="like-post"><img src="icons/like.svg" class="like-icon"><span class="likes-count">8</span></button>

            <button><img src="icons/stats.svg"></button>

            <button><img src="icons/bookmark-grey.svg"></button>

            <button><img src="icons/share.svg"></button>

      </div>

      <div class="comment-box" style="margin-top:10px; display:none;">
        <input type="text" class="comment-input"placeholder="Write a comment..."style="padding:6px; width:75%;">

        <button class="add-comment-btn"style="padding:6px; cursor:pointer;">Add</button>
        <div class="comment-list" style="margin-top:8px;"></div>
      </div>

    </div>
  `;

  return post;
}


tweetBtn.addEventListener("click", () => {
  const text = tweetInput.value.trim();

  if (text === "") {
    alert("Write something before posting!");
    return;

  }

  const newPost = createPost(text);

  postsWrapper.prepend(newPost);

  tweetInput.value = "";

});


postsWrapper.addEventListener("click", (event) => { const likeBtn = event.target.closest(".like-post");

  if (likeBtn) {
    const count = likeBtn.querySelector(".likes-count");
    const icon = likeBtn.querySelector(".like-icon");

    let currentLikes = Number(count.textContent);

    if (likeBtn.classList.contains("liked")) {
      likeBtn.classList.remove("liked");
      count.textContent = currentLikes - 1;

      icon.src = "icons/like.svg";

    } else {
      likeBtn.classList.add("liked");
      count.textContent = currentLikes + 1;

      icon.src = "icons/like-pink.svg";
    }
    return;

  }

  const retweetBtn = event.target.closest(".retweet-post");

  if (retweetBtn) {
    const count = retweetBtn.querySelector(".retweet-count");
    const icon = retweetBtn.querySelector("img");

    count.textContent = Number(count.textContent) + 1;

    retweetBtn.style.color = "green";
    setTimeout(() => {retweetBtn.style.color = "gray"; }, 300);

    icon.src = "icons/retweet-blue.svg";
    return;

  }

  const commentBtn = event.target.closest(".comment-post");

  if (commentBtn) {
    const postContent = commentBtn.closest(".post-content");
    const commentBox = postContent.querySelector(".comment-box");

    commentBox.style.display = commentBox.style.display === "none" ? "block" : "none";
    return;

  }

  const addBtn = event.target.closest(".add-comment-btn");

  if (addBtn) {
    const postContent = addBtn.closest(".post-content");

    const input = postContent.querySelector(".comment-input");
    const list = postContent.querySelector(".comment-list");
    const commentCount = postContent.querySelector(".comment-count");

    const text = input.value.trim();
    if (text === "") return;

    const p = document.createElement("p");
    p.textContent = "• " + text;
    list.appendChild(p);

    commentCount.textContent = Number(commentCount.textContent) + 1;
    input.value = "";

  }
});

const floatingBtn = document.createElement("div");
floatingBtn.className = "floating-tweet-box-icon";
floatingBtn.innerHTML = "+";

document.body.appendChild(floatingBtn);

floatingBtn.addEventListener("click", () => {
  tweetBox.classList.toggle("active");
});
