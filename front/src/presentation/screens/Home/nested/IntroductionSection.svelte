<script>
  const TITLE = 'Hey, I am Jhoel';
  const DESCRIPTION_PART_1 = `
  My interest in web development began with the front-end. I study and work 
  on projects with various frameworks since my technical course. However,
   as I've always been interested in development in general, my career 
   eventually led me to the back-end.`;
  const DESCRIPTION_PART_2 = `
  I have been professionally working with Java and the Spring ecosystem 
  for three years. During this time, I've gained experience in various 
  application architectures and development best practices. These include 
  Ports and Adapters, Domain-Driven Design, Behavior-Driven Development 
  and other types of automated testing, continuous integration and delivery,
   and system observability. Currently, I also have the opportunity to practice
    my English on a daily basis, within a global team.
  `;
  const USERNAME = '@bagnascojhoel';
  const BLOG_URL = 'https://blog.bagnascojhoel.com.br';
</script>

<section
  class="
    mb-16
  "
>
  <span class="text-primary-variant font-mono text-base">{USERNAME}</span>
  <h1
    class="
      text-primary
      mb-5
      leading-relaxed
      font-mono
      text-3xl
      lg:text-5xl
      font-medium
    "
  >
    {TITLE}
  </h1>
  <p class="text-base text-primary-variant max-w-3xl lg:max-w-lg mt-2">
    {DESCRIPTION_PART_1}
  </p>
  <p class="text-base text-primary-variant max-w-3xl lg:max-w-lg mt-2">
    {DESCRIPTION_PART_2}
  </p>
  <p class="text-base text-primary-variant max-w-3xl lg:max-w-lg mt-5">
    I also have a blog for some things I want to share. 
    <a href={BLOG_URL} target="_blank" rel="noreferrer" class="underline">Checkout my blog</a>.
  </p>
</section>
