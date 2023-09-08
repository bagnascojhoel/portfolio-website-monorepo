import { siteConfig } from "./lib/site-config";

const ROOT_PAGE_ID = "676e2b566d4b4e4cb706a06383b74b30";
export default siteConfig({
  // the site's root Notion page (required)
  rootNotionPageId: ROOT_PAGE_ID,

  // if you want to restrict pages to a single notion workspace (optional)
  // (this should be a Notion ID; see the docs for how to extract this)
  rootNotionSpaceId: null,

  // basic site info (required)
  name: "@bagnascojhoel | blog",
  domain: "blog.bagnascojhoel.com.br",
  author: "Jhoel Bagnasco",

  // open graph metadata (optional)
  description: "Personal blog website",

  // social usernames (optional)
  //twitter: "transitive_bs",
  github: "bagnascojhoel",
  linkedin: "bagnascojhoel",
  // mastodon: '#', // optional mastodon profile URL, provides link verification
  // newsletter: '#', // optional newsletter URL
  // youtube: '#', // optional youtube channel name or `channel/UCGbXXXXXXXXXXXXXXXXXXXXXX`

  // default notion icon and cover images for site-wide consistency (optional)
  // page-specific values will override these site-wide defaults
  defaultPageIcon: null,
  defaultPageCover: null,
  defaultPageCoverPosition: 0.5,

  // whether or not to enable support for LQIP preview images (optional)
  isPreviewImageSupportEnabled: true,

  // whether or not redis is enabled for caching generated preview images (optional)
  // NOTE: if you enable redis, you need to set the `REDIS_HOST` and `REDIS_PASSWORD`
  // environment variables. see the readme for more info
  isRedisEnabled: false,

  // map of notion page IDs to URL paths (optional)
  // any pages defined here will override their default URL paths
  // example:
  //
  // pageUrlOverrides: {
  //   '/foo': '067dd719a912471ea9a3ac10710e7fdf',
  //   '/bar': '0be6efce9daf42688f65c76b89f8eb27'
  // }
  pageUrlOverrides: null,

  // whether to use the default notion navigation style or a custom one with links to
  // important pages
  navigationStyle: "default",
  // navigationStyle: 'custom',
  // navigationLinks: [
  //   {
  //     title: "Home",
  //     pageId: ROOT_PAGE_ID,
  //   },
  // ],
});
