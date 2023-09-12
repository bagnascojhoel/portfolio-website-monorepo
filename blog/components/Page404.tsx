import * as React from 'react'
import { useNotionContext } from 'react-notion-x'
import cs from 'classnames';

import * as types from '@/lib/types'
import {rootNotionPageId} from '@/lib/config'


import { NotionPageHeader } from './NotionPageHeader'
import { PageHead } from './PageHead'
import styles from './styles.module.css'

export const Page404: React.FC<types.PageProps> = ({ site, pageId }) => {
  const title = site?.name || 'Page Not Found'
  const { components, mapPageUrl } = useNotionContext();

  return (
    <>
      <PageHead site={site} title={title} />
      <NotionPageHeader/>

      <div className={styles.container}>
        <main className={styles.main}>
          <h1 className={cs('notion-h-title', 'text-color')}>Notion Page Not Found</h1>
          <p className={cs('text-color')}>
            This page is not available, <a href="/" className={cs('notion-link')}> click here to go home.</a>
          </p>

          <img
            src='/404.png'
            alt='404 Not Found'
            className={styles.errorImage}
          />
        </main>
      </div>
    </>
  )
}
